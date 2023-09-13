package com.example.pium.repository;

import com.example.pium.entity.BankProductDataEntity;
import com.example.pium.entity.SavingEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<SavingEntity, Integer> {
    Optional<SavingEntity> findByUserNoAndBankProductDataNo(UserEntity userNo, BankProductDataEntity productNo);
}
