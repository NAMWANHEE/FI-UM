package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikeArtPK implements Serializable {
    private Integer likeArtNo;
    private ArtAuctionEntity auctionNo;
}
