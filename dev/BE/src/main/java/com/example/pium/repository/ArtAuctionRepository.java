package com.example.pium.repository;

import com.example.pium.dto.projection.AuctionClose;
import com.example.pium.entity.ArtAuctionEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtAuctionRepository extends JpaRepository<ArtAuctionEntity, Integer> {

    List<ArtAuctionEntity> findAll();
    Optional<ArtAuctionEntity> findByAuctionNo(Integer auctionNo);
    List<ArtAuctionEntity> findByUserNo(UserEntity userNo);
    List<ArtAuctionEntity> findAllByWinner(UserEntity userNo);
    Optional<ArtAuctionEntity> findByUserNoAndWinnerIsNull(UserEntity user);

    @Query(value = "select a.auction_no as auctionNo,a.user_no as childNo, a.auction_price as auctionPrice, b.userNo as sponsorNo from (SELECT * FROM backend.art_auction where created_time < unix_timestamp(NOW())*1000-(24*60*60*1000) and winner_no is null) as a left join (SELECT auction_no, MAX(bid_price)  as price, MAX(user_no) as userNo FROM backend.bid_record group by auction_no) as b on a.auction_no = b.auction_no",nativeQuery = true)
    List<AuctionClose> getAuctionCloseList();
}
