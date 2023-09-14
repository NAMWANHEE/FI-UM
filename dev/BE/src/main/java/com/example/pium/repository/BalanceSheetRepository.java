package com.example.pium.repository;

import com.example.pium.dto.UserNoDto;
import com.example.pium.entity.BalanceSheetEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheetEntity, Integer> {
    Optional<BalanceSheetEntity> findByUserNo(UserEntity userNo);

    // 예금을 현재 들고 있는 아이의 고유번호를 반환하는 리스트
    @Query(value = "select b.user_no from balance_sheet as b where b.deposit > 0", nativeQuery = true)
    List<Integer> haveDeposit();

    // 적금을 현재 들고 있는 아이의 고유번호를 반환하는 리스트
    @Query(value = "select b.user_no from balance_sheet as b where b.saving > 0", nativeQuery = true)
    List<Integer> haveSaving();

}
