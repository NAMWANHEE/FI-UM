package com.example.pium.dto;

import lombok.Data;

@Data
public class NewItemDto {
    private String name;
    private Integer unitPrice;
    private Integer count;
    private String description;
    private String imagePath;
}
