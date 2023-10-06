package com.example.pium.dto;

import lombok.Data;

@Data
public class SponserShipDto {
    private String name;
    private Double unit_price;
    private Integer count;
    private String description;
    private String image_path;

}
