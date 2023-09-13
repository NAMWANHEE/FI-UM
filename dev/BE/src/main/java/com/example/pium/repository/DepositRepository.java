package com.example.pium.repository;

import com.example.pium.entity.DepositEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {
    List<DepositEntity> findAllByUserNo(UserEntity user);
}
