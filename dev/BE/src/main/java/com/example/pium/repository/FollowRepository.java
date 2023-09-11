package com.example.pium.repository;

import com.example.pium.entity.FollowEntity;
import com.example.pium.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowEntity,Integer> {

    Optional<FollowEntity> findByFollowingAndFollower(UserEntity following, UserEntity follower);
}
