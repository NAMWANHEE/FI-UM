package com.example.pium.controller;


import com.example.pium.dto.QuizDto;
import com.example.pium.dto.ReturnMessageDto;
import com.example.pium.dto.projection.Quiz;
import com.example.pium.service.PointServiceImp;
import com.example.pium.service.QuizService;
import com.example.pium.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/quiz")
@RestController
@Slf4j
public class QuizController {

    private final QuizService quizService;
    private final UserServiceImp userService;
    private final PointServiceImp pointService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getQuiz(){
        log.info("request to /api/v1/quiz [Method: GET]");
        return ResponseEntity.ok(quizService.getQuiz());
    }

    @PostMapping
    public ResponseEntity<ReturnMessageDto> finishQuiz(HttpServletRequest request, @RequestBody List<QuizDto> quizDtoList){
        log.info("request to /api/v1/quiz [Method: POST]");
        Integer userNo = (Integer) request.getAttribute("userNo");
        int answerCount = quizService.checkQuizList(quizDtoList,userNo);
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        int point = 500;
        if(answerCount < 6) {
            returnMessageDto.setMsg("불합격");
            log.info("유저번호 : "+userNo+" | 퀴즈 맞춘 개수가 "+answerCount+"개 이므로 불합격.");
            return ResponseEntity.ok(returnMessageDto);
        }
        returnMessageDto.setMsg("합격");
        log.info("유저번호 : "+userNo+" | 퀴즈 맞춘 개수가 "+answerCount+"개 이므로 합격.");
        if(userService.checkChild(userNo)){  // 퀴즈를 맞춘 사람이 아이인 경우는 포인트 지급
            pointService.makePointRecord(userService.getUserInfo(userNo),pointService.getPointType("퀴즈"),point); // 포인트 내역 갱신
            quizService.updateBalanceSheet(userNo,point);
            quizService.updateUserPoint(userNo,point);
        }
        return ResponseEntity.ok(returnMessageDto);
    }
}
