package com.example.pium.controller;

import com.example.pium.dto.ChildUserDto;
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

    @GetMapping
    public ResponseEntity<List<ChildUserInterface>> checkFollowing(HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        List<ChildUserInterface> childUserDtoList = followService.getFollowing(userNo);
        return ResponseEntity.ok(childUserDtoList);
    }
}
