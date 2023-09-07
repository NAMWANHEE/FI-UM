package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DepositPK implements Serializable{
    private Integer depositNo;
    private Integer userNo;
}
