package com.example.pium.repository;

import com.example.pium.dto.projection.SponsorUserInterface;
import com.example.pium.dto.projection.UserBalanceSheetInterface;
import com.example.pium.dto.projection.UserDepositSavingInterface;
import com.example.pium.entity.FollowEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserId(String userId);

    Optional<UserEntity> findByUserNo(Integer userNo);
    List<UserEntity> findByUserType(Integer type);

    @Query(value = "select a.user_no as userNo,user_name as userName,a.point,a.cash,b.sponsoredAmount from user a ,(select user_no,sum(point_change) as sponsoredAmount from point_record where point_type_no = 1 and user_no = 1 group by user_no) b  where a.user_no = b.user_no", nativeQuery = true)
    SponsorUserInterface findByUserNoAndUserType(int userNo);

    @Query(value = "select b.user_name as userName, a.user_no as userNo, a.point, stock, stock_income as stockIncome, deposit, deposit_income as depositIncome, saving, saving_income as savingIncome, auction_income as auctionIncome, quiz_income as quizIncome, donation from user b, balance_sheet as a where a.user_no = b.user_no and a.user_no = :userNo",nativeQuery = true)
    UserBalanceSheetInterface findUserBalanceSheetByUserNo(Integer userNo);

    @Query(value = "select max(nt.interest_rate) as interestRate, max(prime_interest_rate) as primeInterestRate,type as productType,bank_name as bankName,max(deposit_money) as savingBalance from(\n" +
            "select b.interest_rate,b.prime_interest_rate, b.type, b.bank_name, a.deposit_money \n" +
            "from  (SELECT user_no, bank_product_data_no, MAX(create_deposit) AS max_create_deposit, deposit_money\n" +
            "FROM deposit\n" +
            "WHERE (bank_product_data_no, create_deposit) IN (\n" +
            "    SELECT bank_product_data_no, max(create_deposit)\n" +
            "    FROM deposit\n" +
            "    GROUP BY bank_product_data_no, user_no\n" +
            ")\n" +
            "GROUP BY user_no, bank_product_data_no, deposit_money) as a, \n" +
            "(select n.interest_rate,n.prime_interest_rate, n.type, bl.bank_name,n.bank_product_data_no\n" +
            "from bank_list as bl, \n" +
            "(select pt.type, ss.interest_rate,ss.bank_no,ss.prime_interest_rate,ss.bank_product_data_no\n" +
            "from bank_product_data as ss, product_type as pt where ss.product_no = pt.product_no) as n where bl.bank_no = n.bank_no) as b \n" +
            "                        where a.bank_product_data_no =b.bank_product_data_no and a.user_no = :userNo) as nt group by nt.bank_name,nt.type;",nativeQuery = true)
    List<UserDepositSavingInterface> findByUserDeposit(Integer userNo);


    @Query(value = "select a.saving_balance as savingBalance, b.interest_rate as interestRate, b.prime_interest_rate as primeInterestRate, b.bank_name as bankName,b.type as productType \n" +
            "from saving as a, (\n" +
            "select n.interest_rate,n.prime_interest_rate, n.type, bl.bank_name,n.bank_product_data_no\n" +
            "from bank_list as bl, \n" +
            "(select pt.type, ss.interest_rate,ss.bank_no,ss.prime_interest_rate,ss.bank_product_data_no\n" +
            "from bank_product_data as ss, product_type as pt where ss.product_no = pt.product_no) as n where bl.bank_no = n.bank_no) as b\n" +
            "where a.bank_product_data_no = b.bank_product_data_no and user_no = :userNo",nativeQuery = true)
    List<UserDepositSavingInterface> findByUserSaving(Integer userNo);



}
