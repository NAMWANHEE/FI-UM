package com.example.pium.repository;

import com.example.pium.dto.MyFundingDto;
import com.example.pium.entity.ItemListEntity
        ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ItemListRepository extends JpaRepository<ItemListEntity, Integer> {
    List<ItemListEntity> findAll();
    ItemListEntity findByItemNo(Integer itemNo);
    List<ItemListEntity> findAllByIsCompleted(Boolean bool);

    @Query("SELECT SUM(i.fundingAmount) FROM ItemListEntity i WHERE i.isCompleted = true")
    Integer findTotalFundingAmountForCompletedItems();

    @Query("SELECT SUM(i.sponsorshipAmount) FROM ItemListEntity i WHERE i.isCompleted = true")
    Integer findTotalSponsorshipAmountForCompletedItems();
    @Query("SELECT new com.example.pium.dto.MyFundingDto(i.itemNo, i.itemName , i.itemImagePath , i.itemUnitPrice , i.itemCount , i.fundingAmount) " +
            "FROM ItemListEntity i " +
            "where i.fundingAmount < (i.itemUnitPrice *i.itemCount * 0.3) AND i.isCompleted = true"
            )
    List<MyFundingDto> findFunding();

}
