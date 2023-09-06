package com.example.pium.entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false)
    private int userNo;

    @Column(name = "user_type", nullable = true, columnDefinition = "int default 3")
    private Integer userType;

    @Column(name = "user_name", nullable = false, length = 10)
    private String userName;

    @Column(name = "user_id", nullable = false, length = 20, unique = true)
    private String userId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "join_date", nullable = false)
    private BigInteger joinDate; // Consider using LocalDateTime instead of long

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name = "point", nullable = false, columnDefinition = "int default 0")
    private int point;

    @Column(name = "image_path", length = 255)
    private String imagePath;

    @Column(name = "is_primed1", nullable = false, columnDefinition = "boolean default false")
    private boolean isPrimed1;

    @Column(name = "is_primed2", nullable = false, columnDefinition = "boolean default false")
    private boolean isPrimed2;

    @Column(name = "cash", nullable = false, columnDefinition = "int default 0")
    private int cash;

    @Column(name = "rival")
    private Integer rival;

    // getters, setters, and other methods...

}
