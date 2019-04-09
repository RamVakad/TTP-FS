package com.example.assessment.service;

import com.example.assessment.dto.stock.OrderDTO;
import com.example.assessment.exception.CustomException;
import com.example.assessment.model.Stock;
import com.example.assessment.model.Transaction;
import com.example.assessment.model.User;
import com.example.assessment.model._enum.TxnType;
import com.example.assessment.repository.StockRepository;
import com.example.assessment.repository.TransactionRepository;
import com.example.assessment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TransactionRepository txnService;

    public List<Stock> getPortfolio(User user) {
        return stockRepository.findAllByOwnerEquals(user);
    }

    public boolean buyStock(User user, OrderDTO order) {
        if (order.getAmount() < 1) return false;

        //TODO: Logging
        //TODO: Critical Code
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

            Transaction txn = new Transaction();
            txn.setOwner(user);
            txn.setDate(System.currentTimeMillis());
            txn.setType(TxnType.TXN_BUY);
            txn.setTicker(order.getTicker());
            txn.setPrice(buyPrice);
            txn.setAmount(order.getAmount());
            txnService.save(txn);

            Stock s = new Stock();
            s.setTicker(order.getTicker());
            s.setAmount(order.getAmount());
            s.setOwner(user);
            stockRepository.save(s);

            user.setBalance(user.getBalance().subtract(totalCashRequired));
            userRepository.save(user);

            return true;
        } else {
            throw new CustomException("Insufficient Balance", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
