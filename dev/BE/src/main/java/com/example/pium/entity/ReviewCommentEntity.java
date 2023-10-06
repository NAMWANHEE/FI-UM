package com.example.pium.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder
@DynamicInsert
@Table(name = "REVIEW_COMMENT")
public class ReviewCommentEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="comment_no")
    private Integer commentNo;

    @NotNull
    @Column(name="comment_content")
    private String commentContent;

    @Column(name = "create_time")
    @NotNull
    private BigInteger createTime;


    @NotNull
    @ManyToOne
    @JoinColumn(name="review_no")
    private ReviewBoardEntity reviewNo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_no")
    private UserEntity userNo;
}
