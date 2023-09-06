package com.example.pium.entity;

import javax.persistence.*;

@Entity
@Table(name="BANK_LIST")
public class BankListEntity {

    @Id
    @Column(name="bank_no")
    private int bankNo;

    @Column(name="bank_name", nullable=false, length=20)
    private String bankName;

    // Default Constructor
    public BankList() {}

    // Parametrized Constructor
    public BankList(int bankNo, String bankName) {
        this.bankNo = bankNo;
        this.bankName = bankName;
    }

    // Getters and Setters
    public int getBankNo() {
        return bankNo;
    }

    public void setBankNo(int bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    // equals, hashCode, and toString (생략되었습니다.)
}
