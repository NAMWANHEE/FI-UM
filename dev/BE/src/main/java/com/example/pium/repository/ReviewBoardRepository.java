package com.example.pium.repository;
import com.example.pium.entity.ReviewBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoardEntity, Integer> {
}