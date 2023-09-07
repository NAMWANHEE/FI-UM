package com.example.pium.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigInteger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Entity
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Table(name="STOCK_NEWS")
@IdClass(StockNewsPK.class)
public class StockNewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "newsNo must not be null")
    @Column(name = "news_no")
    private Integer newsNo;

    @Id
    @NotNull(message = "newsNo must not be null")
    @ManyToOne
    @JoinColumn(name = "stock_no")
    private StockEventEntity stockNo;

    @NotNull(message = "newsNo must not be null")
    @Column(name = "news_title")
    private String newsTitle;

    @Lob
    @NotNull(message = "newsNo must not be null")
    @Column(name = "news_content")
    private String newsContent;

    @NotNull(message = "newsNo must not be null")
    @Column(name = "news_time")
    private BigInteger newsTime;
}
