package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockAccountPK implements Serializable{
    private Integer accountNo;
    private Integer userNo;
    private Integer stockNo;
}
