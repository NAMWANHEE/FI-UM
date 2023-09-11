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
@Table(name="STOCK_NEWS")
public class StockNewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "newsNo must not be null")
    @Column(name = "news_no")
    private Integer newsNo;

    @NotNull(message = "newsNo must not be null")
    @ManyToOne
    @JoinColumn(name = "stock_no")
    private StockEventEntity stockNo;

    @NotNull(message = "newsNo must not be null")
    @Column(name = "news_title")
    private String newsTitle;

    @Lob
    @NotNull(message = "newsNo must not be null")
    @Column(name = "news_content")
    private String newsContent;

}
