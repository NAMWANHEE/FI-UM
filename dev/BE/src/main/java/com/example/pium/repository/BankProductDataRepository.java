package com.example.pium.repository;

import com.example.pium.entity.BankListEntity;
import com.example.pium.entity.BankProductDataEntity;
import com.example.pium.entity.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankProductDataRepository extends JpaRepository<BankProductDataEntity, Integer> {
    BankProductDataEntity findByBankNoAndProductNo(BankListEntity bankNo, ProductTypeEntity productNo);
}
