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
@Table(name="QUIZ_CONTENT")
public class QuizContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "quizNo must not be null")
    @Column(name = "quiz_no")
    private Integer quizNo;

    @NotNull(message = "quizContent must not be null")
    @Column(name = "quiz_content")
    @Lob
    private String quizContent;

    @NotNull(message = "quizAnswer must not be null")
    @Column(name = "quiz_answer")
    private Boolean quizAnswer;
}
