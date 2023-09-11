package com.example.pium.repository;

import com.example.pium.dto.SponsorUserInterface;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByUserNo(Integer userNo);


    @Query(value = "select a.user_no as userNo,user_name as userName,a.point,a.cash,b.sponsoredAmount from user a ,(select user_no,sum(point_change) as sponsoredAmount from point_record where point_type_no = 1 and user_no = 1 group by user_no) b  where a.user_no = b.user_no", nativeQuery = true)
    SponsorUserInterface findByUserNoAndUserType(int userNo);
}
