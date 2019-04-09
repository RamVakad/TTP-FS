package com.example.assessment.repository;

import com.example.assessment.model.Transaction;
import com.example.assessment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOwnerEquals(User owner); //TODO: Implement pageable interface.
}