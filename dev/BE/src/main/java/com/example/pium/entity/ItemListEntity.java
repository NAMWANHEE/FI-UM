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
@Table(name="ITEM_LIST")
public class ItemListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_no")
    private Integer itemNo;

    @NotNull(message = "itemName must not be null")
    @Column(name = "item_name")
    private String itemName;

    @NotNull(message = "itemUnitPrice must not be null")
    @Column(name = "item_unit_price")
    private Integer itemUnitPrice;

    @Builder.Default
    @Column(name = "item_count")
    private Integer itemCount =1;

    @NotNull(message = "itemDescription must not be null")
    @Column(name = "item_description")
    private String itemDescription;

    @NotNull(message = "itemImagePath must not be null")
    @Column(name = "item_image_path")
    private String itemImagePath;

    @Builder.Default
    @Column(name = "sponsorship_amount")
    private Integer sponsorshipAmount = 0;

    @Builder.Default
    @Column(name = "funding_amount")
    private Integer fundingAmount = 0;

    @Builder.Default
    @Column(name = "is_completed")
    private Boolean isCompleted = false;
}
