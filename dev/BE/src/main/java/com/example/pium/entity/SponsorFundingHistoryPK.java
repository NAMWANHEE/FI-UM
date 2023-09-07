package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SponsorFundingHistoryPK implements Serializable{
    private Integer fundingNo;
    private Integer itemNo;
    private Integer userNo;
}
