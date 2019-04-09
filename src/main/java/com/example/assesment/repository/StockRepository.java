package com.example.assesment.repository;

import com.example.assesment.model.Stock;
import com.example.assesment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByOwnerEquals(User owner);
}