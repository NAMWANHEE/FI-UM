package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockNewsPK implements Serializable{
    private Integer newsNo;
    private Integer stockNo;
}
