package com.example.pium.controller;

import com.example.pium.dto.SignUpDto;
import com.example.pium.dto.TokenResponseDto;
import com.example.pium.dto.UserLoginDto;
import com.example.pium.entity.UserEntity;
import com.example.pium.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@Slf4j

public class UserController {
    private final UserServiceImp userService;

    @PostMapping("login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserLoginDto userLoginDto){
        if(userService.check(userLoginDto)){
            TokenResponseDto tokenResponseDto = userService.getTokenResponse(userService.getUserNo(userLoginDto.getId()));
            return ResponseEntity.ok(tokenResponseDto);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("signup")
    public Map<String,String> signUp(@RequestBody SignUpDto signUpDto){
        System.out.println(signUpDto);
        UserEntity userEntity = UserEntity.builder()
                .userId(signUpDto.getId())
                .userName(signUpDto.getName())
                .password(BCrypt.hashpw(signUpDto.getPassword(), BCrypt.gensalt()))
                .phoneNumber(signUpDto.getPhoneNumber())
                .joinDate(BigInteger.valueOf(System.currentTimeMillis()))
                .imagePath(null)
                .build();

        userService.save(userEntity);
        Map<String, String> returnMsg = new HashMap<>();
        returnMsg.put("msg","회원 가입이 성공적으로 완료되었습니다.");
        return returnMsg;

    }

    @GetMapping("check-id")
    public ResponseEntity<Map<String, String>> userCheckId(@RequestParam String userId) {
        System.out.println(userId);
        // 아이디가 있는지 여부 파악
        boolean exists = userService.isUserIdExist(userId);

        Map<String, String> returnMsg = new HashMap<>();
        if (exists) {
            returnMsg.put("msg","사용 불가능");
        } else {
            returnMsg.put("msg","사용 가능");
        }
        return new ResponseEntity<>(returnMsg, HttpStatus.OK);
    }


}

