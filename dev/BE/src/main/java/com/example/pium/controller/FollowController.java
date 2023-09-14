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
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<ReturnMessageDto> following(@RequestHeader HttpHeaders header, @RequestBody UserNoDto userNoDto){
        String refreshToken = header.getFirst("X-ACCESS-TOKEN");
        int userNo = Integer.valueOf(jwtTokenProvider.getUserNo(refreshToken));
        int followingUserNo = userNoDto.getUserNo();
        ReturnMessageDto returnMessageDto = followService.doFollowing(userNo, followingUserNo);
        return ResponseEntity.ok(returnMessageDto);
    }

    // 내가 팔로우 하는 아이들 목록 조회
    @GetMapping
    public ResponseEntity<List<ChildUserInterface>> checkFollowing(HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        List<ChildUserInterface> childUserDtoList = followService.getFollowing(userNo);
        return ResponseEntity.ok(childUserDtoList);
    }
}
