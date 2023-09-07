package com.example.pium.entity;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ArtAuctionPK implements Serializable{

    private Integer auctionNo;
    private Integer user;
}
