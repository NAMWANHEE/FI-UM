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
@Table(name="RANKING")
public class RankingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "rankingNo must not be null")
    @Column(name = "ranking_no")
    private Integer rankingNo;

    @NotNull(message = "rankingType must not be null")
    @Column(name="ranking_type")
    private String rankingType;

    @Builder.Default
    @ManyToOne
    @JoinColumn(name="ranking_no1")
    private UserEntity rankingNo1 = null;

    @Builder.Default
    @ManyToOne
    @JoinColumn(name="ranking_no2")
    private UserEntity rankingNo2 = null;

    @Builder.Default
    @ManyToOne
    @JoinColumn(name="ranking_no3")
    private UserEntity rankingNo3 = null;

}
