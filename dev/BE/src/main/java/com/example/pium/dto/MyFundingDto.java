package com.example.pium.dto;

import lombok.Data;

@Data
public class MyFundingDto {
    private String itemName;
    private String imagePath;
    private Integer itemUnitPrice;
    private Integer itemCount;
    private Integer fundingAmount;

    public MyFundingDto(String itemName, String imagePath, Integer itemUnitPrice, Integer itemCount, Long fundingAmount) {
        this.itemName = itemName;
        this.imagePath = imagePath;
        this.itemUnitPrice = itemUnitPrice;
        this.itemCount = itemCount;
        this.fundingAmount = fundingAmount.intValue();
    }
}
