package com.example.pium.repository;

import com.example.pium.dto.projection.SavingAll;
import com.example.pium.entity.BankProductDataEntity;
import com.example.pium.entity.SavingEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<SavingEntity, Integer> {
    Optional<SavingEntity> findByUserNoAndBankProductDataNo(UserEntity userNo, BankProductDataEntity productNo);

    @Query(value = "select s.saving_balance as savingBalance, s.saving_no as savingNo ,s.create_saving as createSaving,s.user_no as userNo,n.interest_rate as interestRate,n.prime_interest_rate as primeInterestRate,n.bank_name as bankName from saving as s, (SELECT a.interest_rate, a.prime_interest_rate, b.bank_name, a.bank_product_data_no FROM bank_product_data as a, bank_list as b where a.bank_no = b.bank_no) as n where s.bank_product_data_no = n.bank_product_data_no ", nativeQuery = true)
    List<SavingAll> findAllSaving();
}
