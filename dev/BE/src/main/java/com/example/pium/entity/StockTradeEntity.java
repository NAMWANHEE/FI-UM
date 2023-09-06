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
@Table(name="STOCK_TRADE")
public class StockTradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "tradeNo must not be null")
    @Column(name="trade_no")
    private Integer tradeNo;

    @NotNull(message = "tradePrice must not be null")
    @Column(name="trade_price")
    private Integer tradePrice;

    @NotNull(message = "tradeCount must not be null")
    @Column(name="trade_count")
    private Integer tradeCount;

    @NotNull(message = "tradeTime must not be null")
    @Column(name="trade_time")
    private BigInteger tradeTime;

    @NotNull(message = "stockNo must not be null")
    @Column(name="stock_no")
    private Integer stockNo;

    @NotNull(message = "userNo must not be null")
    @Column(name="user_no")
    private Integer userNo;
}
