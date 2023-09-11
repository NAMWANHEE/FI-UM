package com.example.pium.service;

import antlr.Token;
import com.example.pium.dto.TokenResponseDto;
import com.example.pium.dto.UserLoginDto;
import com.example.pium.entity.RefreshTokenEntity;
import com.example.pium.entity.UserEntity;
<<<<<<< HEAD
import com.example.pium.repository.UserRepository;
=======
import com.example.pium.repositiory.RefreshTokenRedisRepository;
import com.example.pium.repositiory.UserRepository;
import com.example.pium.util.JwtTokenProvider;
>>>>>>> a695be629543d1f96f2f035ba3a95369c4925a21
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp {

    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public void save(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    public boolean isUserIdExist(String userId) {
        return userRepository.findByUserId(userId).isPresent();

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

    public UserEntity getUserInfo(Integer userNo) { return userRepository.findByUserNo(userNo).get();}
}

