package com.example.pium.repository;

import com.example.pium.entity.SponsorFundingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SponsorFundingHistoryRepository extends JpaRepository<SponsorFundingHistoryEntity, Integer> {

    @Query(value = "select sum(price) as fundingMoney from sponsor_funding_history group by user_no having user_no = :userNo",nativeQuery = true)
    Optional<Integer> findFundingHistory(Integer userNo);
}
