package com.example.pium.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class AuctionDto {
    private String title;
    private String content;
    private Integer userNo;
    private String name;
    private String itemImagePath;
    private String userImagePath;
    private Integer auctionPrice;
    private Integer instantPrice;
    private Integer viewCount;
    private Integer likeCount;
    private BigInteger createdTime;
    private String winner;
}
