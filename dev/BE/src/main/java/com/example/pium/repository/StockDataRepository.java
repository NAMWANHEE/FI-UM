package com.example.pium.repository;

import com.example.pium.dto.projection.UserStockInterface;
import com.example.pium.entity.StockDataEntity;
import com.example.pium.entity.StockEventEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface StockDataRepository extends JpaRepository<StockDataEntity, Integer> {
    List<StockDataEntity> findBySearchNo(int searchTime);
    StockDataEntity findByStockNoAndSearchNo(StockEventEntity stockNo, int searchTime);
    List<StockDataEntity> findByStockNoAndSearchNoLessThanOrderBySearchNoDesc(StockEventEntity stockNo, Integer searchTime, Pageable pageable);

    @Query(value = "select d.stock_name as stockName, d.now_price as nowPrice, c.stock_average as stockAverage, c.stock_count as stockCount from stock_account as c, " +
            "(SELECT a.stock_no, stock_name, now_price FROM stock_event as a , stock_data as b where a.stock_no = b.stock_no and b.search_no = :searchNo) " +
            "as d where c.stock_no = d.stock_no and c.user_no = :userNo",nativeQuery = true)
    List<UserStockInterface> findByUserStock(Integer userNo, Long searchNo);
}
