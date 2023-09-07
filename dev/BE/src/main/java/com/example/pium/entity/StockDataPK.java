package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockDataPK implements Serializable{
    private Integer dataNo;
    private Integer stockNo;
}
