package com.example.pium.dto;

import lombok.Data;
import lombok.extern.java.Log;

import java.util.List;

@Data
public class ItemListDto {
    private Integer itemNo;
    private String itemName;
    private Integer unitPrice;
    private Integer itemCount;
    private String description;
    private String imagePath;
    private Integer sponsorshipAmount;
    private Integer fundingAmount;
    private Boolean isCompleted;
    private List<FundingRankingDto> fundingRanking;
}
