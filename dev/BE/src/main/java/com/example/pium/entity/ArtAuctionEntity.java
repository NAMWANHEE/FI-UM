package com.example.pium.entity;

import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import java.math.BigInteger;
import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder
@DynamicInsert
@Table(name = "ART_AUCTION")
@IdClass(ArtAuctionPK.class)
public class ArtAuctionEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "auction_no")
    private Integer auctionNo;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_no")
    private UserEntity user;

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
    private Integer viewCount = 0;

    @NotNull
    @Column(name = "auction_price")
    private Integer auctionPrice = 100;

    @NotNull
    @Column(name = "instant_price")
    private Integer instantPrice;

    @ManyToOne
    @JoinColumn(name="winner_no")
    @Builder.Default
    private UserEntity winner = null;

}
