package com.example.pium.entity;


import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder
@DynamicInsert
@Table(name = "like_art")
public class LikeArtEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "like_art_no")
    private Integer likeArtNo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="auction_no")
    private ArtAuctionEntity auctionNo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_no")
    private UserEntity userNo;
}
