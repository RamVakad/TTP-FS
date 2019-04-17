package com.example.assessment.service;

import com.example.assessment.dto.stock.OrderDTO;
import com.example.assessment.exception.CustomException;
import com.example.assessment.model.Stock;
import com.example.assessment.model.Transaction;
import com.example.assessment.model.User;
import com.example.assessment.model._enum.TxnType;
import com.example.assessment.repository.StockRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

import java.math.BigDecimal;
import java.util.List;

//This will still not fix the concurrency issue.
//A Transactional Lock needs to be created and acquired for every user.
//OR implement @Transactional
// https://www.baeldung.com/transaction-configuration-with-jpa-and-spring

@Service
public class CriticalService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserService userService;

    //@Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TransactionService transactionService;

    /*
        Returns all stocks owned by a user.
     */
    public List<Stock> getPortfolio(User user) {
        return stockRepository.findByOwnerEquals(user);
    }

    /*
        Sells a stock at the latest price
     */
    public boolean sellStock(User user, OrderDTO order) {
        if (order.getAmount() < 1) return false;

        order.setTicker(order.getTicker().toUpperCase());
        System.out.println(user.getName() + " put in a sell order for " + order.getAmount() + " shares of " + order.getTicker() + ".");

        Stock stk = stockRepository.findByOwnerEqualsAndTickerEquals(user, order.getTicker());
        if (stk == null || stk.getAmount() < order.getAmount()) {
            throw new CustomException("Insufficient amount of shares.", HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            //Begin call to IEX API.
            final IEXTradingClient iexTradingClient = IEXTradingClient.create();
            final Quote quote = iexTradingClient.executeRequest(new QuoteRequestBuilder()
                    .withSymbol(order.getTicker())
                    .build());
            //System.out.println(quote);
            //End call to IEX API.

            BigDecimal sellPrice = quote.getLatestPrice();
            BigDecimal totalCashToIncrement = sellPrice.multiply(BigDecimal.valueOf(order.getAmount()));
            user.setBalance(user.getBalance().add(totalCashToIncrement));
            Transaction record = transactionService.createTxnRecord(TxnType.TXN_SELL, user, order.getTicker(), sellPrice, order.getAmount());

            Session session = sessionFactory.openSession();
            org.hibernate.Transaction hTxn = session.beginTransaction();
            session.save(user);
            if (stk.getAmount().equals(order.getAmount())) {
                session.delete(stk);
            } else {
                stk.setAmount(stk.getAmount() - order.getAmount());
                session.save(stk);
            }
            session.save(record);
            hTxn.commit();
            session.close();

            System.out.println("Sell Successful");

            return true;
        }
    }

    public boolean buyStock(User user, OrderDTO order) {
        if (order.getAmount() < 1) return false;
        order.setTicker(order.getTicker().toUpperCase());
        System.out.println(user.getName() + " put in a buy order for " + order.getAmount() + " shares of " + order.getTicker() + ".");
        //Begin call to IEX API.
        final IEXTradingClient iexTradingClient = IEXTradingClient.create();
        final Quote quote = iexTradingClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol(order.getTicker())
                .build());
        //System.out.println(quote);
        //End call to IEX API.
        BigDecimal buyPrice = quote.getLatestPrice(); //This is NOT the asking price.
        BigDecimal totalCashRequired = buyPrice.multiply(BigDecimal.valueOf(order.getAmount()));
        System.out.println("Total Cash Required = (" + buyPrice + " * " + order.getAmount() + ") = $" + totalCashRequired);


        if (user.getBalance().compareTo(totalCashRequired) != -1) {
            //Update user's portfolio
            Stock s = stockRepository.findByOwnerEqualsAndTickerEquals(user, order.getTicker());
            if (s == null) {
                s = new Stock();
                s.setTicker(order.getTicker());
                s.setAmount(order.getAmount());
                s.setOwner(user);
            } else {
                s.setAmount(s.getAmount() + order.getAmount());
            }

            Transaction record = transactionService.createTxnRecord(TxnType.TXN_BUY, user, order.getTicker(), buyPrice, order.getAmount());

            user.setBalance(user.getBalance().subtract(totalCashRequired));

            Session session = sessionFactory.openSession();
            org.hibernate.Transaction hTxn = session.beginTransaction();
            session.save(user);
            session.save(s);
            session.save(record);
            hTxn.commit();
            session.close();

            System.out.println("Buy Successful");

            return true;
        } else {
            throw new CustomException("Insufficient Balance", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
