package com.example.pium.repository;

import com.example.pium.entity.StockTradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTradeRepository extends JpaRepository<StockTradeEntity, Integer> {
}
