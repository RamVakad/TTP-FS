package com.example.assesment.repository;

import com.example.assesment.model.Transaction;
import com.example.assesment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOwnerEquals(User owner); //TODO: Implement pageable interface.
}