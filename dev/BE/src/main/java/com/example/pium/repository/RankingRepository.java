package com.example.pium.repository;

import com.example.pium.dto.projection.StockRank;
import com.example.pium.entity.RankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<RankingEntity, Integer> {
    List<RankingEntity> findAll();
    RankingEntity findByRankingType(String rankingType);

    // 주식 수익 기준으로 상위 3명 , 동점자가 있는 경우 현재 주식 계좌에 돈이 많은 순으로 출력
   @Query(value = "SELECT user_no as userNo,stock_income as stockIncome,\n" +
           "\t\t           RANK() OVER (ORDER BY stock_income DESC,stock DESC) AS ranking\n" +
           "                FROM balance_sheet limit 3", nativeQuery = true)
    List<StockRank> getStockRanking();
}
