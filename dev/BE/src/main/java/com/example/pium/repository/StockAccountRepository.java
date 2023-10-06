package com.example.pium.repository;

import com.example.pium.entity.StockAccountEntity;
import com.example.pium.entity.StockEventEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockAccountRepository extends JpaRepository<StockAccountEntity, Integer> {
    Optional<StockAccountEntity> findByUserNoAndStockNo(UserEntity userEntity, StockEventEntity stockEventEntity);
    Optional<List<StockAccountEntity>> findByUserNo(UserEntity userEntity);
}
