package com.example.pium.repository;

import com.example.pium.entity.StockEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockEventRepository extends JpaRepository<StockEventEntity, Integer> {
    Optional<StockEventEntity> findById(Integer stockNo);
}
