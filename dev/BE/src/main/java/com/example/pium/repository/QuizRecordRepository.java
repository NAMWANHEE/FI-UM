package com.example.pium.repository;

import com.example.pium.entity.QuizRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRecordRepository extends JpaRepository<QuizRecordEntity,Integer> {
}
