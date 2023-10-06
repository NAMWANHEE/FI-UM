package com.example.pium.dto;

import lombok.Data;

import java.util.List;

@Data
public class MyFundingDto {
    private Integer itemNo;
    private String itemName;
    private String imagePath;
    private Integer itemUnitPrice;
    private Integer itemCount;
    private Integer fundingAmount;
    private List<FundingRankingDto> fundingRanking;

    public MyFundingDto(Integer itemNo, String itemName, String imagePath, Integer itemUnitPrice, Integer itemCount, Integer fundingAmount) {
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.imagePath = imagePath;
        this.itemUnitPrice = itemUnitPrice;
        this.itemCount = itemCount;
        this.fundingAmount = fundingAmount;
    }
}
