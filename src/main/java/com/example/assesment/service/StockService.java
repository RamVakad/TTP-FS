package com.example.assesment.service;

import com.example.assesment.model.Stock;
import com.example.assesment.model.User;
import com.example.assesment.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getPortfolio(User user) {
        return stockRepository.findByOwnerEquals(user);
    }

}
