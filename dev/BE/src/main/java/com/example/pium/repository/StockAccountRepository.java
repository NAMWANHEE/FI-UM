package com.example.pium.repository;

import com.example.pium.entity.StockAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAccountRepository extends JpaRepository<StockAccountEntity, Integer> {
}
