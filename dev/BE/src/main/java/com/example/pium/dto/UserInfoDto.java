package com.example.pium.dto;

import com.example.pium.entity.UserEntity;
import lombok.Data;

import java.math.BigInteger;

@Data
public class UserInfoDto {

    private Integer userNo;

    private String name;

    private String userId;

    private Integer userType;

    private BigInteger joinDate;

    private String phoneNumber;

    private Integer point;

    private boolean isPrimed1;

    private boolean isPrimed2;

    private TokenResponseDto tokenResponseDto;
}
