package com.example.pium.controller;

import com.example.pium.dto.*;
import com.example.pium.dto.projection.SponsorUserInterface;
import com.example.pium.dto.projection.UserBalanceSheetInterface;
import com.example.pium.dto.projection.UserDepositSavingInterface;
import com.example.pium.dto.projection.UserStockInterface;
import com.example.pium.entity.UserEntity;
import com.example.pium.service.UserServiceImp;
import com.example.pium.util.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
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

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@Slf4j

public class UserController {

    private final UserServiceImp userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("login")
    public ResponseEntity<UserInfoDto> login(@RequestBody UserLoginDto userLoginDto){
        if(userService.check(userLoginDto)){
            TokenResponseDto tokenResponseDto = userService.getTokenResponse(userService.getUserNo(userLoginDto.getId()));
            UserEntity user = userService.getUserInfoById(userLoginDto.getId());

            UserInfoDto userInfoDto = userService.setUserInfo(user);
            userInfoDto.setTokenResponseDto(tokenResponseDto);
            return ResponseEntity.ok(userInfoDto);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("logout")
    public ResponseEntity<Map<String,String>> logout(HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        userService.deleteRefreshToken(userNo);
        Map<String,String> map = new HashMap<>();
        map.put("msg","로그아웃 성공");
        return ResponseEntity.ok(map);
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
    public ResponseEntity<Map<String,String>> registRival(HttpServletRequest request, @RequestBody UserNoDto userNoDto){
        Integer userNo = (Integer) request.getAttribute("userNo");
        Integer rivalNo = userNoDto.getUserNo();
        boolean check = userService.registOrDeleteRival(userNo,rivalNo);
        Map<String,String> map = new HashMap<>();
        if(check){
            map.put("msg","등록 성공");
        }
        else{
            map.put("msg","해제 성공");
        }
        return ResponseEntity.ok(map);
    }

    // 라이벌 변경
    @PutMapping("rival")
    public ResponseEntity<Map<String,String>> updateRival(HttpServletRequest request, @RequestBody UserNoDto userNoDto){
        Integer userNo = (Integer) request.getAttribute("userNo");
        Integer rivalNo = userNoDto.getUserNo();
        userService.updateRival(userNo,rivalNo);
        Map<String,String> map = new HashMap<>();
        map.put("msg","변경 완료");
        return ResponseEntity.ok(map);

    }


    // 전체 아이들의 자산 현황
    @GetMapping("total-capital")
    public List<ChildCapitalDto> getChildCapital(HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        Long startTime =  System.currentTimeMillis()-(1000*60*60*2) ;

        return userService.getTotalCapital(startTime);
    }

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

    @GetMapping("capital/{userNo}")
    public ResponseEntity<UserBalanceSheetInterface> getUserBalanceSheet(@PathVariable("userNo") Integer userNo){
        UserBalanceSheetInterface userBalanceSheetInterface = userService.getUserBalanceSheet(userNo);
        return ResponseEntity.ok(userBalanceSheetInterface);
    }

    @GetMapping("artist/{userNo}")
    public List<UserAuctionDto> getAuctionList(@PathVariable("userNo") int artistNo){

        List<UserAuctionDto> auctionDtoList = userService.getAuctionList(artistNo);

        return auctionDtoList;
    }

    // Access token 만료시 헤더에 refresh token 을 담아서  해당 컨트롤러로 요청
    @GetMapping("reissue")
    public ResponseEntity<Map<String,String>> reissue(@RequestHeader HttpHeaders header){

        Map<String,String> map = new HashMap<>();
        try{
            String refreshToken = header.getFirst("X-REFRESH-TOKEN");
            int userNo = Integer.valueOf(jwtTokenProvider.getUserNo(refreshToken));
            String newAccessToken = JwtTokenProvider.createToken(userNo);
            if(userService.checkRefreshToken(refreshToken)){
                map.put("token",newAccessToken);
                return ResponseEntity.ok(map);
            }
            else{
                map.put("token","다시 로그인 해주세요.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
            }
        }
        catch (ExpiredJwtException e){
            map.put("token","다시 로그인 해주세요.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
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

