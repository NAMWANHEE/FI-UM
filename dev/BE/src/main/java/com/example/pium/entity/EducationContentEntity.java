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
@Table(name="EDUCATION_CONTENT")
public class EducationContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "eduContentNo must not be null")
    @Column(name = "edu_contennt_no")
    private Integer eduContentNo;

    @NotNull(message = "eduTitle must not be null")
    @Column(name = "edu_title")
    private String eduTitle;

    @NotNull(message = "eduType must not be null")
    @Column(name = "edu_type")
    private String eduType;

    @NotNull(message = "eduContent must not be null")
    @Column(name = "edu_content")
    @Lob
    private String eduContent;
}
