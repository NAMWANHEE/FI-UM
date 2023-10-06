package com.example.pium.dto.projection;

import com.example.pium.entity.UserEntity;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;


public interface SponsorUserInterface {

    Integer getUserNo();
    String getUserName();
    Integer getPoint();
    Integer getCash();
    Integer getSponsoredAmount();

}
