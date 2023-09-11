package com.example.pium.repository;

import com.example.pium.dto.RGSAuctionDto;
import com.example.pium.entity.ArtAuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtAuctionRepository extends JpaRepository<ArtAuctionEntity, Integer> {

    List<ArtAuctionEntity> findAll();
    Optional<ArtAuctionEntity> findByAuctionNo(Integer auctionNo);

}
