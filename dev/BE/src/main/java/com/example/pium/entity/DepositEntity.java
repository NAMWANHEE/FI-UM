package com.example.pium.entity;



import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="DEPOSIT")
@IdClass(DepositId.class)
public class DepositEntity {

    @Id
    @Column(name="deposit_no")
    private int depositNo;

    @Id
    @ManyToOne
    @JoinColumn(name="user_no", referencedColumnName="user_no")
    private User user;

    @Column(name="deposit_balance", nullable=false)
    private int depositBalance;

    @Column(name="create_deposit", nullable=false)
    private long createDeposit;

    @Column(name="deposit_money", nullable=false)
    private int depositMoney;

    @ManyToOne
    @JoinColumn(name="bank_no", referencedColumnName="bank_no")
    private BankProductData bank;

    @ManyToOne
    @JoinColumn(name="product_no", referencedColumnName="product_no")
    private BankProductData product;

    // 생성자, getter, setter 등은 생략되었습니다.
}

class DepositId implements Serializable {
    private int depositNo;
    private int userNo; // Note: This should match the type of user_no in User entity

    // 생성자, equals, hashCode 메서드는 생략되었습니다.
}
