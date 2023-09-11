package com.example.pium.repository;

import com.example.pium.entity.PointRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRecordRepository extends JpaRepository<PointRecordEntity, Integer> {

}
