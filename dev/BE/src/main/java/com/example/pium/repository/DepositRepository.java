package com.example.pium.repository;

import com.example.pium.dto.projection.DepositInterest;
import com.example.pium.entity.DepositEntity;
import com.example.pium.entity.UserEntity;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {

    List<DepositEntity> findAllByUserNo(UserEntity user);

    @Query(value = "SELECT d.user_no as userNo,SUM(d.deposit_money) as depositMoney FROM deposit as d where d.create_deposit > :nowTime group by user_no ",nativeQuery = true)
    List<DepositInterest> findChildDeposit(Long nowTime);

}
