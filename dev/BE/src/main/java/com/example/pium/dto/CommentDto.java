package com.example.pium.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CommentDto {

    private Integer commentNo;

    private String userName;

    private Integer userNo;

    private String comment;

    private BigInteger createTime;
}
