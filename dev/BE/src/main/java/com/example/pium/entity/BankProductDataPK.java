package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BankProductDataPK implements Serializable {

    private Integer bankNo;
    private Integer productNo;
}
