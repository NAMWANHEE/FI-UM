package com.example.pium.entity;


import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder
@DynamicInsert
@Table(name = "review_board")
public class ReviewBoardEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "review_no")
    private Integer reviewNo;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private UserEntity userNo;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String content;

    @Column(name = "create_time")
    @NotNull
    private BigInteger createTime;

    @Column(name = "image_path")
    private String imagePath;
}
