package com.example.pium.repository;

import com.example.pium.dto.projection.BankInfo;
import com.example.pium.entity.BankListEntity;
import com.example.pium.entity.BankProductDataEntity;
import com.example.pium.entity.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankProductDataRepository extends JpaRepository<BankProductDataEntity, Integer> {
    BankProductDataEntity findByBankNoAndProductNo(BankListEntity bankNo, ProductTypeEntity productNo);

    @Query(value = "select b.bank_name as bankName, b.interest_rate as interestRate, b.prime_interest_rate as primeInterestRate, a.type as productType, b.description from product_type as a, (SELECT b1.prime_interest_rate, b1.interest_rate, b2.bank_name,b1.product_no, b1.description from bank_product_data b1, bank_list b2 where b1.bank_no = b2.bank_no) as b where a.product_no = b.product_no" , nativeQuery = true)
    List<BankInfo> getBankInfo();
}
