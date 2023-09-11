package com.example.pium.repository;

import com.example.pium.entity.PointTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointTypeRepository extends JpaRepository<PointTypeEntity, Integer> {
    Optional<PointTypeEntity> findByPointType(String type);
}
