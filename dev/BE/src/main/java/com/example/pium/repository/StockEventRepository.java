package com.example.pium.repository;

import com.example.pium.entity.StockEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockEventRepository extends JpaRepository<StockEventEntity, Integer> {

}
