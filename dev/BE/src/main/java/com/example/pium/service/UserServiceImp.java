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


    public UserEntity findByUserNo(Integer userNo) {
        return userRepository.findById(userNo).orElse(null);
    }
    public boolean isUserIdExist(String userId) {
        return userRepository.findByUserId(userId).isPresent();

    }

    public boolean checkValidPoint(Integer userNo, Integer money) {
        int userPoint = (int) userRepository.findByUserNo(userNo).get().getPoint();
        if (userPoint >= money) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkValidCash(Integer userNo, Integer money) {
        int userCash = (int) userRepository.findByUserNo(userNo).get().getCash();
        if (userCash >= money) {
            return true;
        } else {
            return false;
        }
    }

    public List<UserAuctionDto> getAuctionList(int artistNo){
        List<UserAuctionDto> auctionDtoList  = new ArrayList<>();
        List<ArtAuctionEntity> list = artAuctionRepository.findByUserNo(userRepository.findByUserNo(artistNo).get());
        for(ArtAuctionEntity art: list){
            UserAuctionDto userAuctionDto = new UserAuctionDto();
            userAuctionDto.setAuctionNo(art.getAuctionNo());
            userAuctionDto.setTitle(art.getTitle());
            userAuctionDto.setWinner(art.getWinner()== null ? null :art.getWinner().getUserNo());
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

    public void registRival(Integer userNo, Integer rivalNo){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        user.setRival(rivalNo);
        userRepository.save(user);


    }

    public void deleteRival(Integer userNo){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        user.setRival(null);
        userRepository.save(user);
    }

    public boolean existRival(Integer userNo){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        if(user.getRival() != null){
            return true;
        }
        return false;
    }

    public void updateRival(Integer userNo, Integer rivalNo){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        user.setRival(rivalNo);
        userRepository.save(user);
    }

    public List<UserDepositSavingDto> getUserDepositSaving(Integer userNo){

        List<UserDepositSavingInterface> list1 = userRepository.findByUserDeposit(userNo);
        List<UserDepositSavingInterface> list2 = userRepository.findByUserSaving(userNo);
        List<UserDepositSavingInterface> nlist = Stream.concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());

        List<UserDepositSavingDto> userDepositSavingDtoList = new ArrayList<>();
        for(UserDepositSavingInterface u : nlist){
            UserDepositSavingDto userDepositSavingDto = new UserDepositSavingDto();
            userDepositSavingDto.setSavingBalance(u.getSavingBalance());
            userDepositSavingDto.setBankName(u.getBankName());
            userDepositSavingDto.setProductType(u.getProductType());
            userDepositSavingDto.setInterestRate(u.getInterestRate());
            if(!checkPrime(userNo,u.getBankName())){
                userDepositSavingDto.setPrimeInterestRate(0);
            }
            else{
                userDepositSavingDto.setPrimeInterestRate(u.getPrimeInterestRate());
            }
            userDepositSavingDtoList.add(userDepositSavingDto);
        }
        return userDepositSavingDtoList;
    }

    public boolean checkPrime(Integer userNo, String bankName){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        if(bankName.equals("햇살은행")){
            if(user.getIsPrimed1()){
                return true;
            }
            else{
                return false;
            }
        }
        else if(bankName.equals("유니콘은행")){
            if(user.getIsPrimed2()){
                return true;
            }
            else{
                return false;
            }

        }
        return false;
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
        Optional<UserEntity> user = userRepository.findByUserId(userLoginDto.getUserId());
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

    public void updateBalanceSheetPoint(Integer userNo, Integer amount){
        BalanceSheetEntity balanceSheetEntity = balanceSheetRepository.findByUserNo(userRepository.findByUserNo(userNo).get());
        balanceSheetEntity.setPoint(balanceSheetEntity.getPoint()+amount);
        balanceSheetRepository.save(balanceSheetEntity);
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
            BalanceSheetEntity balanceSheetOptional = balanceSheetRepository.findByUserNo(userEntity);
            childCapitalDto.setPoint(balanceSheetOptional.getPoint());
            childCapitalDto.setDepositMoney(balanceSheetOptional.getDeposit());
            childCapitalDto.setStockMoney(balanceSheetOptional.getStock());
            childCapitalDto.setUserNo(userEntity.getUserNo());
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
        userInfoDto.setUserName(user.getUserName());
        userInfoDto.setJoinDate(user.getJoinDate());
        userInfoDto.setImagePath(user.getImagePath());
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
    public boolean checkChild(Integer userNo){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        if(user.getUserType()==2) return true;
        return false;
    }

    public void changeImage(Integer userNo, String imagePath) {
        UserEntity findUser = userRepository.findByUserNo(userNo).get();
        findUser.setImagePath(imagePath);
        userRepository.save(findUser);
    }

}

