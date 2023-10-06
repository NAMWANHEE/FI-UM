package com.example.pium.repository;

import com.example.pium.dto.projection.StockRank;
import com.example.pium.entity.RankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<RankingEntity, Integer> {
    List<RankingEntity> findAll();
    RankingEntity findByRankingType(String rankingType);

}
