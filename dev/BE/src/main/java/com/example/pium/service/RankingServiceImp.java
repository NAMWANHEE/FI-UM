package com.example.pium.service;

import com.example.pium.dto.RankingDto;
import com.example.pium.entity.RankingEntity;
import com.example.pium.repositiory.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingServiceImp {
    private final RankingRepository rankingRepository;

    public List<RankingDto> makeDto() {
        List<RankingEntity> rankingList = rankingRepository.findAll();
        List<RankingDto> rankingDto = new ArrayList<>();

        for(RankingEntity rankingEntity : rankingList) {
            RankingDto tmpRankingDto = new RankingDto();
            tmpRankingDto.setType(rankingEntity.getRankingType());
            tmpRankingDto.setNo1(rankingEntity.getRankingNo1().getUserName());
            tmpRankingDto.setNo2(rankingEntity.getRankingNo2().getUserName());
            tmpRankingDto.setNo3(rankingEntity.getRankingNo3().getUserName());
            rankingDto.add(tmpRankingDto);
        }
        return rankingDto;
    }
}
