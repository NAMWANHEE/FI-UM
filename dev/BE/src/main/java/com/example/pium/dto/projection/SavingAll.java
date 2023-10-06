package com.example.pium.dto.projection;

import java.math.BigInteger;

public interface SavingAll {

    Integer getSavingNo();

    Integer getSavingBalance();

    BigInteger getCreateSaving();

    Integer getUserNo();

    Integer getInterestRate();

    Integer getPrimeInterestRate();

    String getBankName();
}
