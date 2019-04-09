package com.example.assessment.repository;

import com.example.assessment.model.Stock;
import com.example.assessment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByOwnerEquals(User owner);
    Stock findByOwnerEqualsAndTickerEquals(User owner, String ticket);
}