package com.example.pium.service;

import com.example.pium.dto.*;
import com.example.pium.entity.ArtAuctionEntity;
import com.example.pium.entity.RefreshTokenEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.ArtAuctionRepository;
import com.example.pium.repository.RefreshTokenRedisRepository;
import com.example.pium.repository.UserRepository;



import com.example.pium.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImp {

    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final ArtAuctionRepository artAuctionRepository;

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

    public SponsorUserInterface getSponsorData(Integer userNo){
        SponsorUserInterface sponsorUserDto = userRepository.findByUserNoAndUserType(userNo);
        return sponsorUserDto;
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

