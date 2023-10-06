package com.example.pium.dto;

import lombok.Data;

@Data
public class UserStockDto {
    private String stockName;

    private Integer stockCount;

    private Integer stockAverage;

    private Integer nowPrice;
}
