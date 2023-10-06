package com.example.pium.repository;

import com.example.pium.dto.projection.QuizRecord;
import com.example.pium.entity.QuizRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuizRecordRepository extends JpaRepository<QuizRecordEntity,Integer> {

    @Query(value = "SELECT MAX(quiz_record_no)as quizNo, user_no as userNo, MAX(solve_time) as solveTime FROM backend.quiz_record where user_no = :userNo group by user_no",nativeQuery = true)
    QuizRecord getQuizRecord(Integer userNo);
}
