package com.example.pium.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder
@DynamicInsert
@Table(name = "BANK_LIST")
public class BankListEntity {

    @Id
    @Column(name="bank_no")
    private int bankNo;

    @NotNull
    @Column(name="bank_name")
    private String bankName;


}
