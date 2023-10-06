package com.example.pium.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Builder
@Entity
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Table(name="QUIZ_RECORD")
public class QuizRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_record_no")
    private Integer quizRecordNo;

    @ManyToOne
    @NotNull(message = "userNo must not be null")
    @JoinColumn(name = "user_no")
    private UserEntity userNo;


    @NotNull(message = "count must not be null")
    @Column(name = "correct_count")
    private Integer correctCount;

    @NotNull(message = "solveTime must not be null")
    @Column(name = "solve_time")
    private BigInteger solveTime;
}
