package com.example.pium.entity;


import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    private BigInteger createTime;  // BigInteger 대신 LocalDate, LocalDateTime, Timestamp 등을 사용할 수도 있습니다.

    @Column(name = "image_path")
    private String imagePath;
}
