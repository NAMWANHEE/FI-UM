package com.example.pium.dto.projection;

import java.io.Serializable;
import java.math.BigInteger;

public interface ChildPointInterface extends Serializable {

    String getUseType();

    Integer getPointChange();

    BigInteger getChangedTime();
}
