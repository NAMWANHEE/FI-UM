package com.example.pium.repositiory;

import com.example.pium.entity.RankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<RankingEntity, Integer> {
    List<RankingEntity> findAll();
}
