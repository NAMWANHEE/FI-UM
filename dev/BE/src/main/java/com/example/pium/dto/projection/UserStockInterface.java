package com.example.pium.dto.projection;


import java.io.Serializable;

public interface UserStockInterface extends Serializable {

    String getStockName();

    Integer getStockCount();

    Integer getStockAverage();

    Integer getNowPrice();


}
