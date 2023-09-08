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
@Table(name="STOCK_DATA")
@IdClass(StockDataPK.class)
public class StockDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "dataNo must not be null")
    @Column(name = "data_no")
    private Integer dataNo;

    @Id
    @NotNull(message = "stockNo must not be null")
    @ManyToOne
    @JoinColumn(name = "stock_no")
    private StockEventEntity stockNo;

    @NotNull(message = "dataTime must not be null")
    @Column(name = "data_time")
    private BigInteger dataTime;

    @NotNull(message = "nowPrice must not be null")
    @Column(name = "now_price")
    private Integer nowPrice;

    @NotNull(message = "fluctuationPrice must not be null")
    @Column(name = "fluctuation_price")
    private Integer fluctuationPrice;
}