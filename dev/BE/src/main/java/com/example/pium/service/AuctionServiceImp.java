package com.example.pium.service;

import com.example.pium.dto.AuctionDto;
import com.example.pium.dto.RGSAuctionDto;
import com.example.pium.entity.ArtAuctionEntity;
import com.example.pium.entity.BidRecordEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.ArtAuctionRepository;
import com.example.pium.repository.BidRecordRepository;
import com.example.pium.repository.LikeArtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionServiceImp {
    private final ArtAuctionRepository artAuctionRepository;
    private final LikeArtRepository likeArtRepository;
    private final BidRecordRepository bidRecordRepository;

    public void post(ArtAuctionEntity artAuctionEntity){
        artAuctionRepository.save(artAuctionEntity);
    }

    public ArtAuctionEntity getAuctionInfo(Integer auctionNo) {
        ArtAuctionEntity auctionInfo = artAuctionRepository.findByAuctionNo(auctionNo).get();
        return auctionInfo;
    }

    public AuctionDto convertToAuctionDto(Integer auctionNo) {
        ArtAuctionEntity beforeDetail = artAuctionRepository.findByAuctionNo(auctionNo).get();
        AuctionDto dto = new AuctionDto();
        dto.setTitle(beforeDetail.getTitle());
        dto.setContent(beforeDetail.getContent());
        dto.setName(beforeDetail.getUser().getUserName());
        dto.setItemImagePath(beforeDetail.getImagePath());
        dto.setUserImagePath(beforeDetail.getUser().getImagePath());
        dto.setAuctionPrice(beforeDetail.getAuctionPrice());
        dto.setInstantPrice(beforeDetail.getInstantPrice());
        dto.setViewCount(beforeDetail.getViewCount());
        Integer likeCount = likeArtRepository.countByAuctionNo(beforeDetail);
        dto.setLikeCount(likeCount);
        dto.setCreatedTime(beforeDetail.getCreatedTime());
        if (beforeDetail.getWinner() != null) {
            dto.setWinner(beforeDetail.getWinner().getUserName());
        }
        return dto;
    }

    public List<AuctionDto> makeOnGoingList() {
        List<ArtAuctionEntity> allList = artAuctionRepository.findAll();
        List<AuctionDto> onGoingList = new ArrayList<>();

        for (ArtAuctionEntity eachList : allList) {
            if (eachList.getWinner() == null) {
                onGoingList.add(convertToAuctionDto(eachList.getAuctionNo()));
            }
        }
        return onGoingList;
    }

    public void makeRecord(UserEntity userEntity,ArtAuctionEntity artAuctionEntity, RGSAuctionDto rgsAuctionDto) {
        BidRecordEntity bidRecordEntity = BidRecordEntity.builder()
                .userNo(userEntity)
                .auctionNo(artAuctionEntity)
                .bidPrice(rgsAuctionDto.getAuctionPrice())
                .bidTime(BigInteger.valueOf(System.currentTimeMillis()))
                .build();
        bidRecordRepository.save(bidRecordEntity);
    }
}
