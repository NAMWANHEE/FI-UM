package com.example.pium.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigInteger;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder
@DynamicInsert
@Table(name = "art_auction")
public class ArtAuctionEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "auction_no")
    private Integer auctionNo;

    @Id
    @Column(name = "user_no")
    private Integer userNo;

    @NotNull
    @Column
    private String title;

    @NotNull
    @Column(name = "created_time")
    private BigInteger createdTime;

    @NotNull
    @Column(name = "image_path")
    private String imagePath;

    @NotNull
    @Column(name = "view_count")
    @Builder.Default
    private Integer viewCount = 0;

    @NotNull
    @Column(name = "auction_price")
    @Builder.Default
    private Integer auctionPrice = 100;

    @NotNull
    @Column(name = "instant_price")
    private Integer instantPrice;

    @Column
    @Builder.Default
    private Integer winner = null;

}
