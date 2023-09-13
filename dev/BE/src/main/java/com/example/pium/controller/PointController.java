package com.example.pium.controller;

import com.example.pium.dto.projection.ChildPointInterface;
import com.example.pium.service.PointServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/point")
@RestController
@Slf4j
public class PointController {

    private final PointServiceImp pointService;

    @GetMapping
    public ResponseEntity<List<ChildPointInterface>>getPointRecord(HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        return ResponseEntity.ok(pointService.getPointRecord(userNo));
    }
}
