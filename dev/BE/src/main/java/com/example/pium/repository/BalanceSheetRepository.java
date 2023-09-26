package com.example.pium.repository;

import com.example.pium.dto.UserNoDto;
import com.example.pium.dto.projection.AuctionRank;
import com.example.pium.dto.projection.Rank;
import com.example.pium.dto.projection.StockRank;
import com.example.pium.entity.BalanceSheetEntity;
import com.example.pium.entity.BankProductDataEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheetEntity, Integer> {

    BalanceSheetEntity findByUserNo(UserEntity user);

    // 예금을 현재 들고 있는 아이의 고유번호를 반환하는 리스트
    @Query(value = "select b from BalanceSheetEntity as b where b.deposit > 0")
    List<BalanceSheetEntity> haveDeposit();

    // 적금을 현재 들고 있는 아이의 고유번호를 반환하는 리스트
    @Query(value = "select b.user_no from balance_sheet as b where b.saving > 0", nativeQuery = true)
    List<Integer> haveSaving();

    // 주식 수익 기준으로 상위 3명 , 동점자가 있는 경우 현재 주식 계좌에 돈이 많은 순으로 출력
    @Query(value = "SELECT user_no as userNo,stock_income as price,RANK() OVER (ORDER BY stock_income DESC,stock DESC) AS ranking FROM balance_sheet limit 3", nativeQuery = true)
    List<Rank> getStockRanking();

    // 그림 판매 금액 순으로 상위 3명
    @Query(value = "SELECT user_no as userNo, auction_income as price, RANK() OVER (ORDER BY auction_income DESC) AS ranking FROM balance_sheet limit 3",nativeQuery = true)
    List<Rank> getAuctionRanking();

    @Query(value = "SELECT user_no as userNo,donation as price, RANK() OVER (ORDER BY donation DESC) AS ranking FROM balance_sheet limit 3",nativeQuery = true)
    List<Rank> getDonationRanking();

    @Query(value = "select a.userNo as userNo, price, RANK() OVER (ORDER BY price DESC, count DESC) AS ranking   from (select u.user_no as userNo, price, count from user u ,(select user_no, sum(price) as price, count(*) as count from sponsor_funding_history as s group by user_no) as sfh where u.user_no = sfh.user_no and u.user_type = 2) as a",nativeQuery = true)
    List<Rank> getFundingRanking();
}
