package com.example.pium.controller;

import com.example.pium.dto.ChildUserDto;
import com.example.pium.dto.ReturnMessageDto;
import com.example.pium.dto.UserNoDto;
import com.example.pium.dto.projection.ChildUserInterface;
import com.example.pium.entity.FollowEntity;
import com.example.pium.service.FollowService;
import com.example.pium.service.UserServiceImp;
import com.example.pium.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/following")
@RestController
@Slf4j
public class FollowController {

    private final JwtTokenProvider jwtTokenProvider;
    private final FollowService followService;

    // 팔로우 등록/취소
    @PostMapping
    public ResponseEntity<ReturnMessageDto> following(HttpServletRequest request, @RequestBody UserNoDto userNoDto){
        log.info("request to /api/v1/following [Method: [POST]");
        Integer userType = (Integer) request.getAttribute("userType");
        Integer userNo = (Integer) request.getAttribute("userNo");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if(!userType.equals(3)){
            log.error("권한 없음.");
            returnMessageDto.setMsg("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
        int followingUserNo = userNoDto.getUserNo();
        returnMessageDto.setMsg(followService.doFollowing(userNo, followingUserNo).getMsg());
        return ResponseEntity.ok(returnMessageDto);
    }

    // 내가 팔로우 하는 아이들 목록 조회
    @GetMapping
    public ResponseEntity<List<ChildUserInterface>> checkFollowing(HttpServletRequest request){
        log.info("request to /api/v1/following [Method: GET]");
        Integer userType = (Integer) request.getAttribute("userType");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if(!userType.equals(3)){
            log.error("권한 없음.");
            returnMessageDto.setMsg("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        Integer userNo = (Integer) request.getAttribute("userNo");
        List<ChildUserInterface> childUserDtoList = followService.getFollowing(userNo);
        return ResponseEntity.ok(childUserDtoList);
    }
}
