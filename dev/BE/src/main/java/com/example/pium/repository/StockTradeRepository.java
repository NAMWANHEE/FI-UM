package com.example.pium.repository;

import com.example.pium.dto.StockTradeDetailDto;
import com.example.pium.entity.StockEventEntity;
import com.example.pium.entity.StockTradeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTradeRepository extends JpaRepository<StockTradeEntity, Integer> {

    List<StockTradeEntity> findTop10ByStockNoOrderByTradeNoDesc(StockEventEntity stockNo, Pageable pageable);
}
