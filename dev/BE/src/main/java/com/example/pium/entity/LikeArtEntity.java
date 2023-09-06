package com.example.pium.entity;


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

    @Id
    @Column(name = "user_no")
    private Integer userNo;

    @Id
    @Column(name = "auction_no")
    private Integer auctionNo;
}
