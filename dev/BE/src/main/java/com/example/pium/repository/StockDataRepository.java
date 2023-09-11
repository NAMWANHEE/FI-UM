package com.example.pium.repository;

import com.example.pium.entity.StockDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDataRepository extends JpaRepository<StockDataEntity, Integer> {
}
