package com.example.pium.repository;

import com.example.pium.entity.BankListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankListRepository extends JpaRepository<BankListEntity, Integer> {
    BankListEntity findByBankName(String bankName);
}
