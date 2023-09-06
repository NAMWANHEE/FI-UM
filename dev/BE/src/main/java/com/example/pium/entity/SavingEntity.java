package com.example.pium.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="SAVING")
@IdClass(SavingId.class)
public class SavingEntity {

    @Id
    @Column(name="saving_no")
    private int savingNo;

    @Id
    @ManyToOne
    @JoinColumn(name="user_no", referencedColumnName="user_no")
    private User user;

    @Column(name="saving_balance", nullable=false)
    private int savingBalance;

    @Column(name="create_saving", nullable=false)
    private long createSaving;

    @ManyToOne
    @JoinColumn(name="bank_no", referencedColumnName="bank_no")
    private BankProductData bank;

    @ManyToOne
    @JoinColumn(name="product_no", referencedColumnName="product_no")
    private BankProductData product;

    // Default Constructor
    public Saving() {}

    // Parametrized Constructor
    public Saving(int savingNo, User user, int savingBalance, long createSaving, BankProductData bank, BankProductData product) {
        this.savingNo = savingNo;
        this.user = user;
        this.savingBalance = savingBalance;
        this.createSaving = createSaving;
        this.bank = bank;
        this.product = product;
    }


}

class SavingId implements Serializable {
    private int savingNo;
    private int userNo; // Note: This should match the type of user_no in User entity

    // Default Constructor
    public SavingId() {}

    // Parametrized Constructor
    public SavingId(int savingNo, int userNo) {
        this.savingNo = savingNo;
        this.userNo = userNo;
    }

}
