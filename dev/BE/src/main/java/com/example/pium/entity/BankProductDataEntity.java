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
@Table(name = "BANK_PRODUCT_DATA")
public class BankProductDataEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "bank_product_data_no")
    private Integer bankProductDataNo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="bank_no")
    private BankListEntity bankNo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="product_no")
    private ProductTypeEntity productNo;

    @NotNull
    @Builder.Default
    @Column(name="interest_rate")
    private Integer interestRate = 0;

    @Builder.Default
    @Column(name="prime_interest_rate")
    private Integer primeInterestRate = 0;

    @Column(name="prime_condition")
    private String primeCondition;


}

