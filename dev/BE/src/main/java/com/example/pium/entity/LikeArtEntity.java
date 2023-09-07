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
@IdClass(LikeArtPK.class)
public class LikeArtEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "like_art_no")
    private Integer likeArtNo;

    @Id
    @NotNull
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="auction_no", referencedColumnName = "auction_no"),
            @JoinColumn(name="user_no", referencedColumnName = "user_no")
    })
    private ArtAuctionEntity auctionNo;
}
