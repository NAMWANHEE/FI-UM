package com.example.pium.dto;

import lombok.Data;
import java.math.BigInteger;

@Data
public class ReviewBoardDto {
    private Integer reviewNo;
    private Integer userNo;  // UserEntity의 userNo 값을 여기에 인티저로 저장합니다.
    private String title;
    private String content;
    private BigInteger createTime;
    private String imagePath;
}
