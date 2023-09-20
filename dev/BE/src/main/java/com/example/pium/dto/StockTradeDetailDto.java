package com.example.pium.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class StockTradeDetailDto {
    private Integer stockCount;
    private BigInteger tradeTime;

}
