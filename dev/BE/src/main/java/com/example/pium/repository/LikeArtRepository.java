package com.example.pium.repository;

import com.example.pium.entity.ArtAuctionEntity;
import com.example.pium.entity.LikeArtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeArtRepository extends JpaRepository<LikeArtEntity, Integer> {
    Integer countByAuctionNo(ArtAuctionEntity auctionNo);
}
