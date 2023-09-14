package com.example.pium.controller;

import com.example.pium.dto.RankingDto;
import com.example.pium.entity.RankingEntity;
import com.example.pium.service.RankingServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/ranking")
@RestController
@Slf4j
public class RankingController {
    private final RankingServiceImp rankingService;
    @GetMapping
    public ResponseEntity<List<RankingDto>> getRankingList() {
        List<RankingDto> rankingList = rankingService.makeDto();
        return ResponseEntity.ok(rankingList);
    }

}
