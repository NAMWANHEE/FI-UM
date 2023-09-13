package com.example.pium.dto;

import com.example.pium.dto.projection.ChildPointInterface;
import com.example.pium.dto.projection.UserStockInterface;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Data

public class ChildCapitalDto {

    private String userName;

    private Integer point;

    private Integer stockMoney;

    private Integer depositMoney;

    private Integer fundingMoney;

    private List<UserStockInterface> stockList;

    private List<ChildPointInterface> pointRecord;
}
