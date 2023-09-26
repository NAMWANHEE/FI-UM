package com.example.pium.repository;

import com.example.pium.dto.projection.DepositAll;
import com.example.pium.dto.projection.DepositInterest;
import com.example.pium.entity.BalanceSheetEntity;
import com.example.pium.entity.BankProductDataEntity;
import com.example.pium.entity.DepositEntity;
import com.example.pium.entity.UserEntity;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {
    Optional<DepositEntity> findTopByUserNoAndBankProductDataNoOrderByDepositNoDesc(UserEntity user, BankProductDataEntity productData);


    List<DepositEntity> findAllByUserNo(UserEntity user);

    @Query(value = "select a.interest_rate as interestRate, a. prime_interest_rate as primeInterestRate, b.userNo as userNo, b.depositMoney as depositMoney from bank_product_data as a, (SELECT d.user_no as userNo, SUM(d.deposit_money) as depositMoney, MAX(d.bank_product_data_no) as bankProductDataNo FROM deposit as d where d.create_deposit > :nowTime group by user_no) as b where a.bank_product_data_no = b.bankProductDataNo ",nativeQuery = true)
    List<DepositInterest> findChildDeposit(Long nowTime);

    @Query(value = "select q.bank_name as bankName, q.interest_rate as interestRate, q.prime_interest_rate as primeInterestRate, q.userNo as userNo, q.depositMoney as totalMoney , w.depositMoney as recentMoney from\n" +
            "(select aa.bank_name, bb.interest_rate, bb.prime_interest_rate, bb. userNo, bb.depositMoney from bank_list as aa, (select a.bank_no,a.bank_product_data_no, a.interest_rate, a. prime_interest_rate, b.userNo, b.depositMoney from bank_product_data as a, (SELECT d.user_no as userNo, SUM(d.deposit_money) as depositMoney, d.bank_product_data_no as bankProductDataNo FROM deposit as d where d.create_deposit > 0 group by user_no, bank_product_data_no) as b where a.bank_product_data_no = b.bankProductDataNo) as bb where aa.bank_no = bb.bank_no) as q left join\n" +
            "(select aa.bank_name, bb.interest_rate, bb.prime_interest_rate, bb. userNo, bb.depositMoney from bank_list as aa, (select a.bank_no,a.bank_product_data_no, a.interest_rate, a. prime_interest_rate, b.userNo, b.depositMoney from bank_product_data as a, (SELECT d.user_no as userNo, SUM(d.deposit_money) as depositMoney, d.bank_product_data_no as bankProductDataNo FROM deposit as d where d.create_deposit > :nowTime group by user_no, bank_product_data_no) as b where a.bank_product_data_no = b.bankProductDataNo) as bb where aa.bank_no = bb.bank_no) as w\n" +
            "on q.bank_name = w.bank_name and q.userNo = w.userNo", nativeQuery = true)
    List<DepositAll> findAllDeposit(Long nowTime);

}
