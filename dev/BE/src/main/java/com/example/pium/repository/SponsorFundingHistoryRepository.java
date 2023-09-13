package com.example.pium.repository;

import com.example.pium.dto.MyFundingDto;
import com.example.pium.entity.ItemListEntity;
import com.example.pium.entity.SponsorFundingHistoryEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SponsorFundingHistoryRepository extends JpaRepository<SponsorFundingHistoryEntity, Integer> {
    List<SponsorFundingHistoryEntity> findAllByUserNo(UserEntity user);

    @Query("SELECT s FROM SponsorFundingHistoryEntity s WHERE s.price < 0")
    List<SponsorFundingHistoryEntity> findAllByPriceLessThanZero();


    @Query("SELECT s.userNo FROM SponsorFundingHistoryEntity s WHERE s.itemNo = :item GROUP BY s.userNo ORDER BY SUM(s.price) DESC")
    List<UserEntity> findTopFunderByItem(@Param("item") ItemListEntity item, Pageable pageable);

    @Query("SELECT new com.example.pium.dto.MyFundingDto(i.itemName , i.itemImagePath , i.itemUnitPrice , i.itemCount , SUM(s.price)) " +
            "FROM SponsorFundingHistoryEntity s " +
            "JOIN s.itemNo i " +
            "WHERE s.userNo.userNo = :userNo " +
            "GROUP BY i.itemNo, i.itemName, i.itemImagePath, i.itemUnitPrice, i.itemCount")
    List<MyFundingDto> findMyFundingByUserNo(@Param("userNo") Integer userNo);

    @Query("SELECT new com.example.pium.dto.MyFundingDto(i.itemName , i.itemImagePath , i.itemUnitPrice , i.itemCount , SUM(s.price)) " +
            "FROM SponsorFundingHistoryEntity s " +
            "JOIN s.itemNo i " +
            "GROUP BY i.itemNo, i.itemName, i.itemImagePath, i.itemUnitPrice, i.itemCount")
    List<MyFundingDto> findFunding();

    @Query(value = "select sum(price) as fundingMoney from sponsor_funding_history group by user_no having user_no = :userNo",nativeQuery = true)
    Optional<Integer> findFundingHistory(Integer userNo);
}


