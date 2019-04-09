package com.example.assessment.service;

import com.example.assessment.model.Transaction;
import com.example.assessment.model.User;
import com.example.assessment.repository.TransactionRepository;
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
