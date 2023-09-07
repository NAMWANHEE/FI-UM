package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BalanceSheetPK implements Serializable{
    private Integer balanceNo;
    private Integer userNo;
}
