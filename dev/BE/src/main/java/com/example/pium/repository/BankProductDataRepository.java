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

    @Query(value = "select bankName,interestRate,primeInterestRate,productType,description,deposit_balance as depositMoney,saving_balance as savingBalance,create_saving as createSaving from (select bankName,interestRate,primeInterestRate,productType,description,bpdn,deposit_balance from (select b.bank_name as bankName, b.interest_rate as interestRate, b.prime_interest_rate as primeInterestRate, a.type as productType, b.description, b.bank_product_data_no as bpdn from product_type as a, (SELECT b1.prime_interest_rate, b1.interest_rate, b2.bank_name,b1.product_no, b1.description, b1.bank_product_data_no from bank_product_data b1, bank_list b2 where b1.bank_no = b2.bank_no) as b where a.product_no = b.product_no) as a left join (SELECT * FROM backend.deposit where user_no = :userNo order by deposit_no desc limit 1) as b on a.bpdn = b.bank_product_data_no) as a left join (select saving_balance, create_saving, bank_product_data_no from saving where user_no = :userNo) as b on a.bpdn = b.bank_product_data_no" , nativeQuery = true)
    List<BankInfo> getBankInfo(Integer userNo);
}
