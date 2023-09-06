package com.example.pium.entity;

import javax.persistence.*;

@Entity
@Table(name="PRODUCT_TYPE")
public class ProductTypeEntity {

    @Id
    @Column(name="product_no")
    private int productNo;

    @Column(name="type", nullable=false, length=20)
    private String type;

    // Default Constructor
    public ProductType() {}

    // Parametrized Constructor
    public ProductType(int productNo, String type) {
        this.productNo = productNo;
        this.type = type;
    }

    // Getters and Setters
    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // equals, hashCode, and toString (생략되었습니다.)
}
