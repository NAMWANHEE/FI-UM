package com.example.pium.dto;

import lombok.Data;

@Data
public class StockNewsDto {
    private Integer newsNo;
    private String newsTitle;
    private String newContent;
    private Integer searchNo;
    private Integer stockNo;
    private String stockName;
}
