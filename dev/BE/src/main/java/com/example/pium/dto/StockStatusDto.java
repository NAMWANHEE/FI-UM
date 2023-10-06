package com.example.pium.dto;

import lombok.Data;

@Data
public class StockStatusDto {
    private String stockName;
    private Integer stockNo;
    private Integer stockCount;
    private Integer stockUnitPrice;
    private Integer stockNowPrice;
}
