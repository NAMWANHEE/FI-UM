package com.example.pium.dto;

import lombok.Data;

@Data
public class StockDataDto {
    private Integer stockNo;
    private String stockName;
    private Integer nowPrice;
    private Integer fluctuationPrice;
    private Integer searchNo;
}
