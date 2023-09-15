package com.example.pium.service;

import com.example.pium.dto.AuctionDto;
import com.example.pium.dto.RGSAuctionDto;
import com.example.pium.dto.UserAuctionDto;
import com.example.pium.entity.*;
import com.example.pium.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionServiceImp {
    private final ArtAuctionRepository artAuctionRepository;
    private final LikeArtRepository likeArtRepository;
    private final BidRecordRepository bidRecordRepository;
    private final PointTypeRepository pointTypeRepository;
    private final BalanceSheetRepository balanceSheetRepository;
    private final PointServiceImp pointService;
    private final UserServiceImp userService;


    public void post(ArtAuctionEntity artAuctionEntity){
        artAuctionRepository.save(artAuctionEntity);
    }

    public ArtAuctionEntity getAuctionInfo(Integer auctionNo) {
        ArtAuctionEntity auctionInfo = artAuctionRepository.findByAuctionNo(auctionNo).get();
        return auctionInfo;
    }

    public Boolean postAuction(Integer user, RGSAuctionDto rgsAuctionDto) {
        Optional<ArtAuctionEntity> checkData = artAuctionRepository.findByUserNoAndWinnerIsNull(userService.getUserInfo(user));
        if (!checkData.isPresent()) {
            ArtAuctionEntity artAuctionEntity = ArtAuctionEntity.builder()
                    .userNo(userService.getUserInfo(user))
                    .title(rgsAuctionDto.getTitle())
                    .content(rgsAuctionDto.getContent())
                    .createdTime(BigInteger.valueOf(System.currentTimeMillis()))
                    .imagePath(rgsAuctionDto.getImagePath())
                    .instantPrice(rgsAuctionDto.getInstantPrice())
                    .build();
            post(artAuctionEntity);
            return true;
        }else {
            return false;
        }
    }

    public AuctionDto convertToAuctionDto(Integer auctionNo) {
        ArtAuctionEntity beforeDetail = artAuctionRepository.findByAuctionNo(auctionNo).get();
        AuctionDto dto = new AuctionDto();
        dto.setAuctionNo(beforeDetail.getAuctionNo());
        dto.setUserNo(beforeDetail.getUserNo().getUserNo());
        dto.setTitle(beforeDetail.getTitle());
        dto.setContent(beforeDetail.getContent());
        dto.setName(beforeDetail.getUserNo().getUserName());
        dto.setItemImagePath(beforeDetail.getImagePath());
        dto.setUserImagePath(beforeDetail.getUserNo().getImagePath());
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

    public void changePoint(UserEntity buyer, UserEntity seller, Integer price){
        PointTypeEntity pointType = pointTypeRepository.findByPointType("경매").get();
        // 입찰자의 포인트 감소, 판매자 포인트 증가 내역 추가
        pointService.makePointRecord(buyer, pointType, -price);
        pointService.makePointRecord(seller, pointType, price);
        // 포인트 테이블 변경
        pointService.changePointTable(buyer, price);
        pointService.changePointTable(seller, -price);

        // 재무상태표에 반영
        BalanceSheetEntity sellerBalance = balanceSheetRepository.findByUserNo(seller).get();
        sellerBalance.setAuctionIncome(sellerBalance.getAuctionIncome()+price);
        balanceSheetRepository.save(sellerBalance);
    }

    public List<UserAuctionDto> getPurchaseArt(Integer user){
        List<UserAuctionDto> artData = new ArrayList<>();
        List<ArtAuctionEntity> getArtData = artAuctionRepository.findAllByWinner(userService.getUserInfo(user));
        for (ArtAuctionEntity eachData : getArtData) {
            UserAuctionDto tmpDto = new UserAuctionDto();
            tmpDto.setAuctionNo(eachData.getAuctionNo());
            tmpDto.setImagePath(eachData.getImagePath());
            tmpDto.setTitle(eachData.getTitle());
            tmpDto.setWinner(eachData.getWinner().getUserNo());
            artData.add(tmpDto);
        }
        return artData;
    }
}
