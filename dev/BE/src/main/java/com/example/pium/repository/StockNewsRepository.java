package com.example.pium.repository;

import com.example.pium.entity.StockDataEntity;
import com.example.pium.entity.StockEventEntity;
import com.example.pium.entity.StockNewsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockNewsRepository extends JpaRepository<StockNewsEntity, Integer> {
    @Query("SELECT sn FROM StockNewsEntity sn " +
            "WHERE sn.searchNo = (SELECT MAX(s.searchNo) FROM StockNewsEntity s WHERE s.stockNo = sn.stockNo AND s.searchNo <= :searchNo)")
    List<StockNewsEntity> findLatestNewsByStockBelowSearchNo(@Param("searchNo") Integer searchNo);

    List<StockNewsEntity> findByStockNoAndSearchNoLessThanEqualOrderBySearchNoDesc(StockEventEntity stockNo, Integer searchTime, Pageable pageable);
}
