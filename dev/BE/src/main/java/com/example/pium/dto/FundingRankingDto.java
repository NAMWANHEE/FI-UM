package com.example.pium.dto;

import lombok.Data;

@Data
public class FundingRankingDto {
    private String userName;
    private Long userFundingAmount;

    public FundingRankingDto(String userName, Long userFundingAmount) {
        this.userName = userName;
        this.userFundingAmount = userFundingAmount;
    }
}
