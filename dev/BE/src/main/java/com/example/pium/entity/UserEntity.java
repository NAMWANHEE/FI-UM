package com.example.pium.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import java.math.BigInteger;
import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Builder
@DynamicInsert
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Integer userNo;

    @Builder.Default
    @Column(name = "user_type")
    private Integer userType = 3;

    @NotNull(message = "userName must not be null")
    @Column(name = "user_name")
    private String userName;

    @NotNull(message = "userId must not be null")
    @Column(name = "user_id")
    private String userId;

    @Column
    @NotNull(message = "password must not be null")
    private String password;

    @NotNull(message = "joinDate must not be null")
    @Column(name = "join_date")
    private BigInteger joinDate;

    @Builder.Default
    @Column(name = "phone_number")
    private String phoneNumber = null;

    @Builder.Default
    @NotNull(message = "isDeleted must not be null")
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Builder.Default
    @NotNull(message = "point must not be null")
    @Column
    private Integer point = 0;

    @Column(name = "image_path")
    private String imagePath;

    @Builder.Default
    @NotNull(message = "isPrimed1 must not be null")
    @Column(name = "is_primed1")
    private Boolean isPrimed1 = false;

    @Builder.Default
    @NotNull(message = "isPrimed2 must not be null")
    @Column(name = "is_primed2")
    private Boolean isPrimed2 = false;

    @Builder.Default
    @NotNull(message = "cash must not be null")
    @Column
    private Integer cash = 0;

    @Builder.Default
    @Column
    private Integer rival = null;


}
