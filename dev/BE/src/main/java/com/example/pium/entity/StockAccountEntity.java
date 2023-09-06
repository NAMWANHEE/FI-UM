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
@Table(name="STOCK_ACCOUNT")
public class StockAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "accountNo must not be null")
    @Column(name = "account_no")
    private Integer accountNo;

    @NotNull(message = "userNo must not be null")
    @Column(name = "user_no")
    private Integer userNo;

    @NotNull(message = "stockNo must not be null")
    @Column(name = "stock_no")
    private Integer stockNo;

    @Builder.Default
    @Column(name = "stock_count")
    private Integer stockCount = 0;

    @Builder.Default
    @Column(name = "stcok_average")
    private Integer stockAverage = 0;
}
