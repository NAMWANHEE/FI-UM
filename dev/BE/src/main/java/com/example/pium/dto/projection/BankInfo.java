package com.example.pium.dto.projection;

import java.math.BigInteger;

public interface BankInfo {

    String getBankName();

    String getProductType();

    Integer getInterestRate();

    Integer getPrimeInterestRate();

    String getDescription();

    Integer getDepositMoney();

    Integer getSavingBalance();

    BigInteger getCreateSaving();


}
