package com.example.assessment.service;

import com.example.assessment.model.Transaction;
import com.example.assessment.model.User;
import com.example.assessment.model._enum.TxnType;
import com.example.assessment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    /*
        Get all transactions of a user.
     */
    public List<Transaction> getAllTransactions(User user) {
        return transactionRepository.findByOwnerEquals(user);
    }

    /*
        Creates a record of transaction.
     */
    public void createTxnRecord(TxnType type, User user, String ticker, BigDecimal price, Integer amount) {
        Transaction txn = new Transaction();
        txn.setType(type);
        txn.setOwner(user);
        txn.setTicker(ticker);
        txn.setPrice(price);
        txn.setAmount(amount);
        txn.setDate(System.currentTimeMillis());
        transactionRepository.save(txn);
    }
}
