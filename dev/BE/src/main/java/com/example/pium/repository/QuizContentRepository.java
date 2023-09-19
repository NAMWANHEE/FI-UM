package com.example.pium.repository;

import com.example.pium.dto.projection.Quiz;
import com.example.pium.entity.QuizContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizContentRepository extends JpaRepository<QuizContentEntity, Integer> {

    @Query(value = "SELECT q.quiz_no as quizNo, q.quiz_answer as answer, q.quiz_content as quiz FROM quiz_content as q ORDER BY RAND() LIMIT 10",nativeQuery = true)
    List<Quiz> getQuiz();
}
