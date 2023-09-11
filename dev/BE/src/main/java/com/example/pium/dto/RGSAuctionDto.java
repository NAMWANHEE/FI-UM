package com.example.pium.dto;

import lombok.Data;

@Data
public class RGSAuctionDto {
    private String title;
    private String content;
    private String imagePath;
    private Integer instantPrice;
    private Integer auctionPrice;
}
