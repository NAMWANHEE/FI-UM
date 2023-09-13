package com.example.pium.service;

import com.example.pium.dto.*;
import com.example.pium.dto.projection.*;
import com.example.pium.entity.ArtAuctionEntity;
import com.example.pium.entity.BalanceSheetEntity;
import com.example.pium.entity.RefreshTokenEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.*;


import com.example.pium.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImp {

    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final ArtAuctionRepository artAuctionRepository;
    private final StockDataRepository stockDataRepository;
    private final PointRecordRepository pointRecordRepository;
    private final BalanceSheetRepository balanceSheetRepository;
    private final SponsorFundingHistoryRepository sponsorFundingHistoryRepository;

    public void save(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    public boolean isUserIdExist(String userId) {
        return userRepository.findByUserId(userId).isPresent();

    }

    public List<UserAuctionDto> getAuctionList(int artistNo){
        List<UserAuctionDto> auctionDtoList  = new ArrayList<>();
        List<ArtAuctionEntity> list = artAuctionRepository.findByUserNo(userRepository.findByUserNo(artistNo).get());
        for(ArtAuctionEntity art: list){
            UserAuctionDto userAuctionDto = new UserAuctionDto();
            userAuctionDto.setAuctionNo(art.getAuctionNo());
            userAuctionDto.setTitle(art.getTitle());
            userAuctionDto.setWinner(art.getWinner().getUserNo());
            userAuctionDto.setImagePath(art.getImagePath());
            auctionDtoList.add(userAuctionDto);
        }
        return auctionDtoList;
    }

    public Integer getType(int userNo){
        return userRepository.findByUserNo(userNo).get().getUserType();
    }

    public ChildUserDto getChildData(Integer userNo){
        ChildUserDto childUserDto = new ChildUserDto();
        UserEntity user = userRepository.findByUserNo(userNo).get();
        childUserDto.setUserNo(user.getUserNo());
        childUserDto.setUserName(user.getUserName());
        childUserDto.setImagePath(user.getImagePath());
        return childUserDto;
    }

    public boolean registOrDeleteRival(Integer userNo, Integer rivalNo){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        if(user.getRival() == null){ // 라이벌 등록
            user.setRival(rivalNo);
            userRepository.save(user);
            return true;
        }
        else{                       // 라이벌 해제
            user.setRival(null);
            userRepository.save(user);
            return false;
        }

    }

    public void updateRival(Integer userNo, Integer rivalNo){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        user.setRival(rivalNo);
        userRepository.save(user);
    }

    public List<UserDepositSavingInterface> getUserDepositSaving(Integer userNo){
        List<UserDepositSavingInterface> list1 = userRepository.findByUserDeposit(userNo);
        System.out.println(list1);
        List<UserDepositSavingInterface> list2 = userRepository.findByUserSaving(userNo);
        System.out.println(list2);
        return Stream.concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());
    }

    public SponsorUserInterface getSponsorData(Integer userNo){
        SponsorUserInterface sponsorUserDto = userRepository.findByUserNoAndUserType(userNo);
        return sponsorUserDto;
    }

    public UserBalanceSheetInterface getUserBalanceSheet(Integer userNo){
        return userRepository.findUserBalanceSheetByUserNo(userNo);
    }

    public Integer getUserNo(String userId){
        return userRepository.findByUserId(userId).get().getUserNo();
    }

    public boolean check(UserLoginDto userLoginDto){
        Optional<UserEntity> user = userRepository.findByUserId(userLoginDto.getId());
        if(user.isPresent()&& BCrypt.checkpw(userLoginDto.getPassword(), user.get().getPassword())){
            return true;
        }
        else return false;
    }

    public void deleteRefreshToken(int userNo){
        refreshTokenRedisRepository.deleteById(userNo);
    }


    public TokenResponseDto getTokenResponse(int userNo){
        String accessToken = JwtTokenProvider.createToken(userNo);
        String refreshToken = JwtTokenProvider.createRefreshToken(userNo);
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .refreshToken(refreshToken)
                .userNo(userNo)
                .build();
        refreshTokenRedisRepository.save(refreshTokenEntity);
        TokenResponseDto tokenResponseDto = new TokenResponseDto();
        tokenResponseDto.setAccessToken(accessToken);
        tokenResponseDto.setRefreshToken(refreshToken);
        return tokenResponseDto;

    }

    public boolean checkRefreshToken(String token){

        if(refreshTokenRedisRepository.findByRefreshToken(token) == null) return false; // 리프레시 토큰이 유효하다면 true 아니라면 false 반환
        else return true;
    }

    public List<ChildCapitalDto> getTotalCapital(Long startTime){
        List<UserEntity>  userList = userRepository.findByUserType(2);
        List<ChildCapitalDto> capitalDtoList = new ArrayList<>();
        for(UserEntity user: userList){
            Integer userNo = user.getUserNo();
            UserEntity userEntity = userRepository.findByUserNo(userNo).get();
            ChildCapitalDto childCapitalDto = new ChildCapitalDto();
            Optional<BalanceSheetEntity> balanceSheetOptional = balanceSheetRepository.findByUserNo(userEntity);
            if (balanceSheetOptional.isPresent()) {
                BalanceSheetEntity balanceSheetEntity = balanceSheetOptional.get();
                childCapitalDto.setPoint(balanceSheetEntity.getPoint());
                childCapitalDto.setDepositMoney(balanceSheetEntity.getDeposit());
                childCapitalDto.setStockMoney(balanceSheetEntity.getStock());
            } else {
                childCapitalDto.setPoint(0);
                childCapitalDto.setDepositMoney(0);
                childCapitalDto.setStockMoney(0);
            }

            childCapitalDto.setUserName(userEntity.getUserName());
            childCapitalDto.setFundingMoney(sponsorFundingHistoryRepository.findFundingHistory(userNo).orElse(0));
            List<UserStockInterface> userStockInterface = stockDataRepository.findByUserStock(userNo,(System.currentTimeMillis()-startTime) / (1000*60*60));
            List<ChildPointInterface> childPointInterfaces = pointRecordRepository.findByUserNo(userNo);
            childCapitalDto.setStockList(userStockInterface);
            childCapitalDto.setPointRecord(childPointInterfaces);
            capitalDtoList.add(childCapitalDto);
        }

        return capitalDtoList;
    }


    public UserInfoDto setUserInfo(UserEntity user){

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(user.getUserId());
        userInfoDto.setUserNo(user.getUserNo());
        userInfoDto.setUserType(user.getUserType());
        userInfoDto.setName(user.getUserName());
        userInfoDto.setJoinDate(user.getJoinDate());
        userInfoDto.setPhoneNumber(user.getPhoneNumber());
        userInfoDto.setPoint(user.getPoint());
        userInfoDto.setPrimed1(user.getIsPrimed1());
        userInfoDto.setPrimed2(user.getIsPrimed2());

        return userInfoDto;
    }

    public UserEntity getUserInfoById(String userId){
        return userRepository.findByUserId(userId).get();
    }
    public UserEntity getUserInfo(Integer userNo) { return userRepository.findByUserNo(userNo).get();}


}

