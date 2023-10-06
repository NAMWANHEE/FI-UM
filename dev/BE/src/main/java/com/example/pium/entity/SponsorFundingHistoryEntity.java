package com.example.pium.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Entity
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Table(name="SPONSOR_FUNDING_HISTORY")
public class SponsorFundingHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_no")
    private Integer fundingNo;


    @NotNull(message = "itemNo must not be null")
    @ManyToOne
    @JoinColumn(name = "item_no")
    private ItemListEntity itemNo;


    @NotNull(message = "userNo must not be null")
    @ManyToOne
    @JoinColumn(name = "user_no")
    private UserEntity userNo;

    @NotNull(message = "price must not be null")
    @Column(name = "price")
    private Integer price;
}
