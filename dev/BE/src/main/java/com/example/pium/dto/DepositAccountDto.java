package com.example.pium.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class DepositAccountDto {
    private Integer bankNo;
    private String bankName;
    private Integer depositBalance;
    private BigInteger createDeposit;
    private Integer depositMoney;
}
