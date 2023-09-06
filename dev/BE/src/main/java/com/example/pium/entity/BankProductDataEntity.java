package com.example.pium.entity;

import javax.persistence.*;

@Entity
@Table(name="BANK_PRODUCT_DATA")
@IdClass(BankProductDataId.class)
public class BankProductDataEntity {

    @Id
    @ManyToOne
    @JoinColumn(name="bank_no", referencedColumnName="bank_no")
    private BankList bank;

    @Id
    @ManyToOne
    @JoinColumn(name="product_no", referencedColumnName="product_no")
    private ProductType product;

    @Column(name="interest_rate", nullable=false)
    private int interestRate;

    @Column(name="prime_interest_rate")
    private Integer primeInterestRate;

    @Column(name="prime_condition")
    private String primeCondition;

    // Getters, Setters, Constructors, equals, hashCode
    // ... (as per your requirements)
}

class BankProductDataId implements Serializable {
    private int bankNo;
    private int productNo;

    // Default and Parametrized Constructors, Getters, Setters, equals, hashCode
    // ... (as per your requirements)
}
