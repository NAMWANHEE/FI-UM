package com.example.pium.controller;

import com.example.pium.dto.SignUpDto;
import com.example.pium.entity.UserEntity;
import com.example.pium.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@Slf4j

public class UserController {

    private final UserService userService;
    @PostMapping("signup")
    public Map<String,String> signUp(SignUpDto signUpDto){
        UserEntity userEntity = UserEntity.builder()
                .userId(signUpDto.getId())
                .userName(signUpDto.getName())
                .password(BCrypt.hashpw(signUpDto.getPassword(), BCrypt.gensalt()))
                .joinDate(BigInteger.valueOf(System.currentTimeMillis()))
                .imagePath(null)
                .build();

        userService.save(userEntity);
        Map<String, String> returnMsg = new HashMap<>();
        returnMsg.put("msg","회원 가입이 성공적으로 완료되었습니다.");
        return returnMsg;
    }

}
