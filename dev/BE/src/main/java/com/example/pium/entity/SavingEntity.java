package com.example.pium.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@Builder
@DynamicInsert
@Table(name = "SAVING")
public class SavingEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="saving_no")
    private Integer savingNo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_no")
    private UserEntity userNo;

    @NotNull
    @Column(name="saving_balance")
    private Integer savingBalance;

    @NotNull
    @Column(name="create_saving")
    private BigInteger createSaving;

    @ManyToOne
    @JoinColumn(name="bank_product_data_no")
    private BankProductDataEntity bankProductDataNo;




}
