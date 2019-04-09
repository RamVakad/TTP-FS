package com.example.assesment.service;

import com.example.assesment.model.Transaction;
import com.example.assesment.model.User;
import com.example.assesment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions(User user) {
        return transactionRepository.findByOwnerEquals(user);
    }
}
