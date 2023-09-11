package com.example.pium.controller;

import com.example.pium.dto.UserNoDto;
import com.example.pium.entity.FollowEntity;
import com.example.pium.service.FollowService;
import com.example.pium.service.UserServiceImp;
import com.example.pium.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/following")
@RestController
@Slf4j
public class FollowController {

    private final JwtTokenProvider jwtTokenProvider;
    private final FollowService followService;

    @PostMapping
    public ResponseEntity<Map<String,String>> following(@RequestHeader HttpHeaders header, @RequestBody UserNoDto userNoDto){
        String refreshToken = header.getFirst("X-ACCESS-TOKEN");
        int userNo = Integer.valueOf(jwtTokenProvider.getUserNo(refreshToken));
        int followingUserNo = userNoDto.getUserNo();
        Map<String,String> map = followService.doFollowing(userNo, followingUserNo);
        return ResponseEntity.ok(map);
    }
}
