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
@Table(name = "balance_sheet")
public class BalanceSheetEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Integer userNo;

    @Column
    @NotNull
    @Builder.Default
    private Integer point = 0;

    @NotNull
    @Column
    @Builder.Default
    private Integer stock = 0;

    @NotNull
    @Column(name = "stock_income")
    @Builder.Default
    private Integer stockIncome = 0;

    @NotNull
    @Column
    @Builder.Default
    private Integer deposit = 0;

    @NotNull
    @Column(name = "deposit_income")
    @Builder.Default
    private Integer depositIncome = 0;

    @NotNull
    @Column
    @Builder.Default
    private Integer saving = 0;

    @NotNull
    @Column(name = "saving_income")
    @Builder.Default
    private Integer savingIncome = 0;

    @NotNull
    @Column(name = "auction_income")
    @Builder.Default
    private Integer auctionIncome = 0;

    @NotNull
    @Column(name = "quiz_income")
    @Builder.Default
    private Integer quizIncome = 0;

    @NotNull
    @Column
    @Builder.Default
    private Integer donation = 0;
}
