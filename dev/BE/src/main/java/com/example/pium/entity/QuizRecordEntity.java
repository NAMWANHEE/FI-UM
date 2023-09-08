package com.example.pium.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "quizRecordNo must not be null")
    @Column(name = "quiz_record_no")
    private Integer quizRecordNo;

    @ManyToOne
    @NotNull(message = "userNo must not be null")
    @JoinColumn(name = "user_no")
    private UserEntity userNo;

    @ManyToOne
    @NotNull(message = "quizNo must not be null")
    @JoinColumn(name = "quiz_no")
    private QuizContentEntity quizNo;

    @NotNull(message = "isCorrected must not be null")
    @Column(name = "is_corrected")
    private Boolean isCorrected;
}