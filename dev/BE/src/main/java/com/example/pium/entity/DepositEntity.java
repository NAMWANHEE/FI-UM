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
@IdClass(DepositPK.class)
public class DepositEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="deposit_no")
    private Integer depositNo;

    @Id
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
    @JoinColumns({
            @JoinColumn(name="bank_no", referencedColumnName = "bank_no"),
            @JoinColumn(name="product_no", referencedColumnName = "product_no")
    })
    private BankProductDataEntity bankProductData;

}

