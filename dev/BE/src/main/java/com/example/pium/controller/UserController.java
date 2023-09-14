package com.example.pium.controller;

import com.example.pium.dto.*;
import com.example.pium.dto.projection.*;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.BalanceSheetRepository;
import com.example.pium.repository.DepositRepository;
import com.example.pium.service.UserServiceImp;
import com.example.pium.util.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = { "유저" })
@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@Slf4j

public class UserController {

    private final UserServiceImp userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BalanceSheetRepository balanceSheetRepository;
    private final DepositRepository depositRepository;

    // 유저 로그인
    @PostMapping("login")
    public ResponseEntity<UserInfoDto> login(@RequestBody UserLoginDto userLoginDto){
        System.out.println(userLoginDto);
        if(userService.check(userLoginDto)){
            TokenResponseDto tokenResponseDto = userService.getTokenResponse(userService.getUserNo(userLoginDto.getUserId()));
            UserEntity user = userService.getUserInfoById(userLoginDto.getUserId());

            UserInfoDto userInfoDto = userService.setUserInfo(user);
            userInfoDto.setTokenResponse(tokenResponseDto);
            return ResponseEntity.ok(userInfoDto);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 유저 로그아웃
    @PostMapping("logout")
    public ResponseEntity<ReturnMessageDto> logout(HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        userService.deleteRefreshToken(userNo);
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        returnMessageDto.setMsg("로그아웃 성공");
        return ResponseEntity.ok(returnMessageDto);
    }

    //특정 아이의 예/적금 조회
    @GetMapping("deposit-saving")
    public ResponseEntity<List<UserDepositSavingInterface>> getUserDepositSaving(HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        System.out.println(userNo);
        List<UserDepositSavingInterface> userDepositSavingInterface = userService.getUserDepositSaving(userNo);
        System.out.println(userDepositSavingInterface);
        return ResponseEntity.ok(userDepositSavingInterface);
    }

    //라이벌 등록 및 취소
    @PostMapping("rival")
    public ResponseEntity<ReturnMessageDto> registRival(HttpServletRequest request, @RequestBody UserNoDto userNoDto){
        Integer userNo = (Integer) request.getAttribute("userNo");
        Integer rivalNo = userNoDto.getUserNo();
        boolean check = userService.registOrDeleteRival(userNo,rivalNo);
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if(check){
            returnMessageDto.setMsg("등록 성공");
        }
        else{
            returnMessageDto.setMsg("해제 성공");
        }
        return ResponseEntity.ok(returnMessageDto);
    }

    // 라이벌 변경
    @PutMapping("rival")
    public ResponseEntity<ReturnMessageDto> updateRival(HttpServletRequest request, @RequestBody UserNoDto userNoDto){
        Integer userNo = (Integer) request.getAttribute("userNo");
        Integer rivalNo = userNoDto.getUserNo();
        userService.updateRival(userNo,rivalNo);
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        returnMessageDto.setMsg("변경 완료");
        return ResponseEntity.ok(returnMessageDto);

    }


    // 전체 아이들의 자산 현황
    @GetMapping("total-capital")
    public List<ChildCapitalDto> getChildCapital(HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        Long startTime =  System.currentTimeMillis()-(1000*60*60*2) ;

        return userService.getTotalCapital(startTime);
    }

    // 개인 정보 조회
    @GetMapping
    public ResponseEntity<?> getMyData(HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        int type = userService.getType(userNo);

        // 아이들 or 원장쌤
       if(type == 2 || type == 1){
            ChildUserDto childUserDto = userService.getChildData(userNo);
           return ResponseEntity.ok(childUserDto);
        }
        // 후원자
        else{
            SponsorUserInterface sponsorUserDto = userService.getSponsorData(userNo);
           return ResponseEntity.ok(sponsorUserDto);
        }


    }

    // 특정 아이의 재무상태표 조회
    @GetMapping("capital/{userNo}")
    public ResponseEntity<UserBalanceSheetInterface> getUserBalanceSheet(@PathVariable("userNo") Integer userNo){
        UserBalanceSheetInterface userBalanceSheetInterface = userService.getUserBalanceSheet(userNo);
        return ResponseEntity.ok(userBalanceSheetInterface);
    }

    // 특정 아이의 예술품(경매품) 전체 조회
    @GetMapping("artist/{userNo}")
    public List<UserAuctionDto> getAuctionList(@PathVariable("userNo") int artistNo){

        List<UserAuctionDto> auctionDtoList = userService.getAuctionList(artistNo);

        return auctionDtoList;
    }

    // Access token 만료시 헤더에 refresh token 을 담아서  해당 컨트롤러로 요청
    @GetMapping("reissue")
    public ResponseEntity<AccessTokenDto> reissue(@RequestHeader HttpHeaders header){

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        try{
            String refreshToken = header.getFirst("X-REFRESH-TOKEN");
            int userNo = Integer.valueOf(jwtTokenProvider.getUserNo(refreshToken));
            String newAccessToken = JwtTokenProvider.createToken(userNo);
            if(userService.checkRefreshToken(refreshToken)){
                accessTokenDto.setToken(newAccessToken);
                return ResponseEntity.ok(accessTokenDto);
            }
            else{
                accessTokenDto.setToken("다시 로그인 해주세요.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenDto);
            }
        }
        catch (ExpiredJwtException e){
            accessTokenDto.setToken("다시 로그인 해주세요.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenDto);
        }


    }

    // 유저 회원가입
    @PostMapping("signup")
    public ResponseEntity<ReturnMessageDto> signUp(@RequestBody SignUpDto signUpDto){

        UserEntity userEntity = UserEntity.builder()
                .userId(signUpDto.getUserId())
                .userName(signUpDto.getUserName())
                .password(BCrypt.hashpw(signUpDto.getPassword(), BCrypt.gensalt()))
                .phoneNumber(signUpDto.getPhoneNumber())
                .joinDate(BigInteger.valueOf(System.currentTimeMillis()))
                .imagePath(null)
                .build();

        userService.save(userEntity);
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        returnMessageDto.setMsg("회원 가입이 성공적으로 완료되었습니다.");
        return ResponseEntity.ok(returnMessageDto);

    }

    // 아이디 중복체크
    @GetMapping("check-id")
    public ResponseEntity<ReturnMessageDto> userCheckId(@RequestParam String userId) {

        // 아이디가 있는지 여부 파악
        boolean exists = userService.isUserIdExist(userId);
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if (exists) {
            returnMessageDto.setMsg("사용 불가능");
        } else {
            returnMessageDto.setMsg("사용 가능");
        }
        return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
    }


}

