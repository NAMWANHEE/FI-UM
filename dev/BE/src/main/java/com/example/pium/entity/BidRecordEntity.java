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
@Table(name = "bid_record")
@IdClass(BidRecordPK.class)
public class BidRecordEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bid_no")
    private Integer bidNo;

    @Id
    @NotNull
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="auction_no", referencedColumnName = "auction_no"),
            @JoinColumn(name="user_no", referencedColumnName = "user_no")
    })
    private ArtAuctionEntity auctionNo;

    @NotNull
    @Column(name = "bid_price")
    private Integer bidPrice;

    @NotNull
    @Column(name = "bid_time")
    private BigInteger bidTime;
}
