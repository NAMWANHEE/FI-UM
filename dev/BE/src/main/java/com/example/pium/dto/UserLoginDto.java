package com.example.pium.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginDto {

    @ApiModelProperty(value = "사용자의 아이디", example = "idle1", required = true)
    private String userId;

    @ApiModelProperty(value = "사용자의 패스워드", example = "1234", required = true)
    private String password;
}
