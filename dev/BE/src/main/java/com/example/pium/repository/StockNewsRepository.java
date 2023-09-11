package com.example.pium.repository;

import com.example.pium.entity.StockNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockNewsRepository extends JpaRepository<StockNewsEntity, Integer> {
}
