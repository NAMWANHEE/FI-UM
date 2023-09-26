package com.example.pium.controller;

import com.example.pium.dto.*;
import com.example.pium.dto.projection.*;
import com.example.pium.entity.BalanceSheetEntity;
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
import java.util.Arrays;
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

    // 유저 로그인
    @PostMapping("login")
    public ResponseEntity<UserInfoDto> login(@RequestBody UserLoginDto userLoginDto){
        log.info("request to /api/v1/user/login [Method: POST]");
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
        log.info("request to /api/v1/user/logout [Method: POST]");
        Integer userNo = (Integer) request.getAttribute("userNo");
        userService.deleteRefreshToken(userNo);
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        returnMessageDto.setMsg("로그아웃 성공");
        return ResponseEntity.ok(returnMessageDto);
    }

    //특정 아이의 예/적금 조회
    @GetMapping("deposit-saving")
    public ResponseEntity<List<UserDepositSavingDto>> getUserDepositSaving(HttpServletRequest request){
        log.info("request to /api/v1/user/deposit-saving [Method: GET]");
        Integer userType = (Integer)request.getAttribute("userType");
        if(!userType.equals(2)){
            log.error("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        Integer userNo = (Integer) request.getAttribute("userNo");
        List<UserDepositSavingDto> userDepositSavingInterface = userService.getUserDepositSaving(userNo);
        return ResponseEntity.ok(userDepositSavingInterface);
    }

    //라이벌 등록
    @PostMapping("rival")
    public ResponseEntity<ReturnMessageDto> registRival(HttpServletRequest request, @RequestBody UserNoDto userNoDto){
        log.info("request to /api/v1/user/rival [Method: POST]");
        Integer userType = (Integer)request.getAttribute("userType");
        if(!userType.equals(2)){
            log.error("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        Integer userNo = (Integer) request.getAttribute("userNo");
        Integer rivalNo = userNoDto.getUserNo();
        boolean check = userService.existRival(userNo);
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if(!check){
            userService.registRival(userNo,rivalNo);
            returnMessageDto.setMsg("등록 성공");
            log.info("유저번호 : "+userNo+" 님이 "+rivalNo+" 님을 라이벌로 등록하였습니다.");
            return ResponseEntity.ok(returnMessageDto);
        }
        else{
            returnMessageDto.setMsg("이미 등록된 라이벌이 있습니다.");
            log.error("이미 등록된 라이벌이 있습니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(returnMessageDto);
        }

    }

    // 라이벌 변경
    @PutMapping("rival")
    public ResponseEntity<ReturnMessageDto> updateRival(HttpServletRequest request, @RequestBody UserNoDto userNoDto){
        log.info("request to /api/v1/user/rival [Method: PUT]");

        Integer userType = (Integer)request.getAttribute("userType");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if(!userType.equals(2)){
            returnMessageDto.setMsg("권한 없음");
            log.error("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
        Integer userNo = (Integer) request.getAttribute("userNo");
        Integer rivalNo = userNoDto.getUserNo();
        userService.updateRival(userNo,rivalNo);
        returnMessageDto.setMsg("변경 완료");
        return ResponseEntity.ok(returnMessageDto);

    }

    // 라이벌 해제
    @DeleteMapping("rival")
    public ResponseEntity<ReturnMessageDto> deleteRival(HttpServletRequest request){
        log.info("request to /api/v1/user/rival [Method: DELETE]");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        Integer userType = (Integer)request.getAttribute("userType");
        if(!userType.equals(2)){
            returnMessageDto.setMsg("권한 없음");
            log.error("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
        Integer userNo = (Integer) request.getAttribute("userNo");
        userService.deleteRival(userNo);
        returnMessageDto.setMsg("해제 완료");
        return ResponseEntity.ok(returnMessageDto);

    }

    // 전체 아이들의 자산 현황
    @GetMapping("total-capital")
    public ResponseEntity<List<ChildCapitalDto>> getChildCapital(HttpServletRequest request){
        log.info("request to /api/v1/user/total-capital [Method: GET]");
        Integer userType = (Integer)request.getAttribute("userType");
        if(!userType.equals(1)){
            log.error("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        Long startTime =  System.currentTimeMillis()-(1000*60*60*2) ;

        return ResponseEntity.ok(userService.getTotalCapital(startTime));
    }

    // 개인 정보 조회
    @GetMapping
    public ResponseEntity<?> getMyData(HttpServletRequest request){
        log.info("request to /api/v1/user [Method: GET]");
        Integer userNo = (Integer) request.getAttribute("userNo");
        int type = userService.getType(userNo);
        log.info("조회한 유저 타입 : "+type);
        // 아이들 or 원장쌤
       if(type == 2 || type == 1){
            ChildUserDto childUserDto = userService.getChildData(userNo);
            System.out.println(childUserDto);
           return ResponseEntity.ok(childUserDto);
        }
        // 후원자
        else{
            SponsorUserInterface sponsorUserDto = userService.getSponsorData(userNo);
            System.out.println(userNo);
            System.out.println(sponsorUserDto);
           return ResponseEntity.ok(sponsorUserDto);
        }

    }

    // 특정 아이의 재무상태표 조회
    @GetMapping("capital/{userNo}")
    public ResponseEntity<UserBalanceSheetInterface> getUserBalanceSheet(@PathVariable("userNo") Integer userNo, HttpServletRequest request){
        log.info("request to /api/v1/user/capital/"+userNo+" [Method: GET]");
        Integer userType = (Integer)request.getAttribute("userType");
        if(!userType.equals(1)){
            log.error("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        UserBalanceSheetInterface userBalanceSheetInterface = userService.getUserBalanceSheet(userNo);
        return ResponseEntity.ok(userBalanceSheetInterface);
    }

    // 특정 아이의 예술품(경매품) 전체 조회
    @GetMapping("artist/{userNo}")
    public List<UserAuctionDto> getAuctionList(@PathVariable("userNo") int artistNo){
        log.info("request to /api/v1/user/artist/"+artistNo+" [Method: GET]");
        List<UserAuctionDto> auctionDtoList = userService.getAuctionList(artistNo);
        return auctionDtoList;
    }

    // Access token 만료시 헤더에 refresh token 을 담아서  해당 컨트롤러로 요청
    @GetMapping("reissue")
    public ResponseEntity<AccessTokenDto> reissue(@RequestHeader HttpHeaders header){
        log.info("request to /api/v1/user/reissue [Method: GET]");
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
                log.error("리프래시 토큰 만료 ==> 다시 로그인 ");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenDto);
            }
        }
        catch (ExpiredJwtException e){
            accessTokenDto.setToken("다시 로그인 해주세요.");
            log.error("리프래시 토큰 만료 ==> 다시 로그인 ");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenDto);
        }
    }

    // 유저 회원가입
    @PostMapping("signup")
    public ResponseEntity<ReturnMessageDto> signUp(@RequestBody SignUpDto signUpDto){
        log.info("request to /api/v1/user/signup [Method: POST]");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if(signUpDto.getUserId().length() < 4 || signUpDto.getPassword().length() < 8  || signUpDto.getUserName().length() < 2){
           returnMessageDto.setMsg("입력 정보를 확인하세요.");
           log.error("입력 정보 에러 ==> 아이디 길이 또는 패스워드 길이 또는 이름 길이를 확인하세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnMessageDto);
        }
        if(userService.isUserIdExist(signUpDto.getUserId())){
            returnMessageDto.setMsg("아이디 중복.");
            log.error("아이디 중복");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(returnMessageDto);
        }
        UserEntity userEntity = UserEntity.builder()
                .userId(signUpDto.getUserId())
                .userName(signUpDto.getUserName())
                .password(BCrypt.hashpw(signUpDto.getPassword(), BCrypt.gensalt()))
                .phoneNumber(signUpDto.getPhoneNumber())
                .joinDate(BigInteger.valueOf(System.currentTimeMillis()))
                .imagePath(null)
                .build();

        userService.save(userEntity);
        log.info("회원가입 완료");
        returnMessageDto.setMsg("회원 가입이 성공적으로 완료되었습니다.");
        return ResponseEntity.ok(returnMessageDto);

    }

    // 아이디 중복체크
    @GetMapping("check-id")
    public ResponseEntity<ReturnMessageDto> userCheckId(@RequestParam String userId) {
        log.info("request to /api/v1/user/check-id [Method: GET]");
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

    @PutMapping("profile-image")
    public  ResponseEntity<ReturnMessageDto> changeImage(HttpServletRequest request, @RequestBody UserImageDto userImage) {
        log.info("request to /api/v1/user/profile-image [Method: PUT]");
        Integer userNo = (Integer) request.getAttribute("userNo");
        userService.changeImage(userNo, userImage.getImagePath());
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        returnMessageDto.setMsg("변경이 완료되었습니다.");
        return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
    }

}

