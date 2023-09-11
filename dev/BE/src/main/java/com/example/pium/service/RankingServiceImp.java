package com.example.pium.service;

import com.example.pium.dto.RankingDto;
import com.example.pium.entity.RankingEntity;
import com.example.pium.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingServiceImp {
    private final RankingRepository rankingRepository;

    public RankingDto convertDto(RankingEntity rankingEntity) {
        RankingDto tmpRankingDto = new RankingDto();
        tmpRankingDto.setType(rankingEntity.getRankingType());
        tmpRankingDto.setNo1No(rankingEntity.getRankingNo1().getUserNo());
        tmpRankingDto.setNo1(rankingEntity.getRankingNo1().getUserName());
        tmpRankingDto.setNo1ImagePath(rankingEntity.getRankingNo1().getImagePath());
        tmpRankingDto.setNo2No(rankingEntity.getRankingNo2().getUserNo());
        tmpRankingDto.setNo2(rankingEntity.getRankingNo2().getUserName());
        tmpRankingDto.setNo2ImagePath(rankingEntity.getRankingNo2().getImagePath());
        tmpRankingDto.setNo3No(rankingEntity.getRankingNo3().getUserNo());
        tmpRankingDto.setNo3(rankingEntity.getRankingNo3().getUserName());
        tmpRankingDto.setNo3ImagePath(rankingEntity.getRankingNo3().getImagePath());
        return tmpRankingDto;
    }

    public List<RankingDto> makeDto() {
        List<RankingEntity> rankingList = rankingRepository.findAll();
        List<RankingDto> rankingDto = new ArrayList<>();

        for(RankingEntity rankingEntity : rankingList) {
            RankingDto tmpRankingDto = convertDto(rankingEntity);
            rankingDto.add(tmpRankingDto);
        }
        return rankingDto;
    }

    public RankingDto takeStockRanking() {
        RankingEntity rankingEntity = rankingRepository.findByRankingType("주식");
        return convertDto(rankingEntity);
    }
}
