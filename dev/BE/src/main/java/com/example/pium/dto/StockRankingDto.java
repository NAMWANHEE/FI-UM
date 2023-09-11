package com.example.pium.dto;

import lombok.Data;

import java.util.List;

@Data
public class StockRankingDto {
    private String userName;
    private String imagePath;
    private List<StockStatusDto> stockList;
}
