package com.example.pium.service;

import com.example.pium.dto.QuizDto;
import com.example.pium.dto.projection.Quiz;
import com.example.pium.entity.BalanceSheetEntity;
import com.example.pium.entity.QuizContentEntity;
import com.example.pium.entity.QuizRecordEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.BalanceSheetRepository;
import com.example.pium.repository.QuizContentRepository;
import com.example.pium.repository.QuizRecordRepository;
import com.example.pium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizContentRepository quizContentRepository;
    private final QuizRecordRepository quizRecordRepository;
    private final UserRepository userRepository;
    private final BalanceSheetRepository balanceSheetRepository;

    public List<Quiz> getQuiz(){
        return quizContentRepository.getQuiz();
    }

    public Integer checkQuizList(List<QuizDto> quizDtoList, Integer userNo) {
        int count = 0; // 맞은 개수 확인

        UserEntity user = userRepository.findByUserNo(userNo).get();

        for(QuizDto quiz : quizDtoList){
            // 퀴즈 기록 저장
            if(quiz.isAnswer()) count++;
        }
        QuizRecordEntity quizRecordEntity = QuizRecordEntity.builder().userNo(user).solveTime(BigInteger.valueOf(System.currentTimeMillis())).correctCount(count).build();
        quizRecordRepository.save(quizRecordEntity);
        return count;
    }

    public void updateBalanceSheet(Integer userNo, Integer point){
        BalanceSheetEntity balanceSheetEntity = balanceSheetRepository.findByUserNo(userRepository.findByUserNo(userNo).get());
        balanceSheetEntity.setPoint(balanceSheetEntity.getPoint()+point);
        balanceSheetEntity.setQuizIncome(balanceSheetEntity.getQuizIncome()+point);
        balanceSheetRepository.save(balanceSheetEntity);
    }

    public void updateUserPoint(Integer userNo, Integer point){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        user.setPoint(user.getPoint()+point);
        userRepository.save(user);
    }
}
