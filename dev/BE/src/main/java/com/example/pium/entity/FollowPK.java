package com.example.pium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class FollowPK implements Serializable{
    private Integer followNo;
    private Integer following;
    private Integer follower;
}
