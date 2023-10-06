package com.example.pium.repository;

import com.example.pium.entity.BidRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRecordRepository extends JpaRepository<BidRecordEntity, Integer> {

}
