package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BidRecordPK implements Serializable{
    private Integer bidNo;
    private ArtAuctionEntity auctionNo;
}
