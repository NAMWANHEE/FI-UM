package com.example.pium.entity;


import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder
@DynamicInsert
@Table(name = "follow")
public class FollowEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "follow_no")
    private Integer followNo;


    @Column
    @NotNull
    private Integer following;


    @Column
    @NotNull
    private Integer follower;
}
