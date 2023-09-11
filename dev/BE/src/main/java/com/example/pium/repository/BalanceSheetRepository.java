package com.example.pium.repository;

import com.example.pium.entity.BalanceSheetEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheetEntity, Integer> {
    Optional<BalanceSheetEntity> findByUserNo(UserEntity userNo);

}
