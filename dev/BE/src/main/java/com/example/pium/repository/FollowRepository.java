package com.example.pium.repository;

import com.example.pium.dto.ChildUserDto;
import com.example.pium.dto.projection.ChildUserInterface;
import com.example.pium.entity.FollowEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowEntity,Integer> {

    Optional<FollowEntity> findByFollowingAndFollower(UserEntity following, UserEntity follower);

    @Query(value = "select user_no as userNo,user_name as userName,image_path as imagePath from user as a where user_no  in (SELECT follower FROM follow where following = :following)", nativeQuery = true)
    List<ChildUserInterface> findByUserNo(UserEntity following);
}
