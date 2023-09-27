package com.example.pium.controller;


import com.example.pium.dto.CommentDto;
import com.example.pium.dto.ReturnMessageDto;
import com.example.pium.service.CommentService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
@Slf4j
public class ReviewCommentController {

    private final CommentService commentService;

    @GetMapping("/{reviewNo}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable(value = "reviewNo") Integer reviewNo){
        return ResponseEntity.ok(commentService.getComments(reviewNo));
    }

    @PostMapping("/{reviewNo}")
    public ResponseEntity<ReturnMessageDto> postComments(@PathVariable(value = "reviewNo") Integer reviewNo, HttpServletRequest request, @RequestBody CommentDto commentDto){
        Integer userNo = (Integer) request.getAttribute("userNo");
        String comment = commentDto.getComment();
        commentService.postComments(reviewNo,comment,userNo);
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        returnMessageDto.setMsg("등록 완료");
        return ResponseEntity.ok(returnMessageDto);
    }

    @PutMapping("/{commentNo}")
    public ResponseEntity<ReturnMessageDto> putComments(@PathVariable(value = "commentNo") Integer commentNo, HttpServletRequest request, @RequestBody CommentDto commentDto){
        Integer userNo = (Integer) request.getAttribute("userNo");
        String comment = commentDto.getComment();
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if (commentService.putComments(commentNo,comment,userNo)){
            returnMessageDto.setMsg("수정 완료");
            return ResponseEntity.ok(returnMessageDto);
        }
        else{
            returnMessageDto.setMsg("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
    }

    @DeleteMapping("/{commentNo}")
    public ResponseEntity<ReturnMessageDto> deleteComments(@PathVariable(value = "commentNo") Integer commentNo, HttpServletRequest request){
        Integer userNo = (Integer) request.getAttribute("userNo");
        Integer userType = (Integer) request.getAttribute("userType");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if(commentService.deleteComments(commentNo,userNo, userType)){
            returnMessageDto.setMsg("삭제 완료");
            return ResponseEntity.ok(returnMessageDto);
        }
        else{
            returnMessageDto.setMsg("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
    }
}
