package com.example.pium.dto;

import lombok.Data;

@Data
public class UserDepositSavingDto {

    private String bankName;

    private String productType;

    private Integer interestRate;

    private Integer primeInterestRate;

    private Integer savingBalance;

}
