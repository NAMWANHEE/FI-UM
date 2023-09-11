package com.example.pium.repository;

import com.example.pium.entity.StockDataEntity;
import com.example.pium.entity.StockEventEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDataRepository extends JpaRepository<StockDataEntity, Integer> {
    List<StockDataEntity> findBySearchNo(int searchTime);
    StockDataEntity findByStockNoAndSearchNo(StockEventEntity stockNo, int searchTime);
    List<StockDataEntity> findByStockNoAndSearchNoLessThanOrderBySearchNoDesc(StockEventEntity stockNo, Integer searchTime, Pageable pageable);
}
