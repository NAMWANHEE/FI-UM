package com.example.pium.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder
@DynamicInsert
@Table(name = "DEPOSIT")
public class DepositEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="deposit_no")
    private Integer depositNo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_no")
    private UserEntity userNo;

    @NotNull
    @Column(name="deposit_balance")
    private Integer depositBalance;

    @NotNull
    @Column(name="create_deposit")
    private BigInteger createDeposit;

    @NotNull
    @Column(name="deposit_money")
    private Integer depositMoney;

    @ManyToOne
    @JoinColumn(name="bank_product_data_no")
    private BankProductDataEntity bankProductDataNo;

}

