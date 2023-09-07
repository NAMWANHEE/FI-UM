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
@Table(name = "point_record")
@IdClass(PointRecordPK.class)
public class PointRecordEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "point_no")
    private Integer pointNo;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_no")
    private UserEntity userNo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "point_type_no")
    private PointTypeEntity pointTypeNo;

    @NotNull
    @Column(name = "pointChange")
    private Integer pointChange;

    @NotNull
    @Column(name = "changed_time")
    private BigInteger changedTime;
}
