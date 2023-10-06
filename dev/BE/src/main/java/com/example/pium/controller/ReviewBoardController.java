package com.example.pium.controller;

import com.example.pium.dto.ReturnMessageDto;
import com.example.pium.entity.UserEntity;
import com.example.pium.service.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;

import com.example.pium.dto.ReviewBoardDto;
import com.example.pium.entity.ReviewBoardEntity;
import com.example.pium.service.ReviewBoardService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Slf4j
public class ReviewBoardController {


    @Autowired
    private ReviewBoardService reviewService;


    @Autowired
    private UserServiceImp userService;






    @PostMapping
    public ResponseEntity<ReviewBoardDto> createReview(HttpServletRequest request, @RequestBody ReviewBoardDto reviewDto) {
        log.info("request to /api/v1/reviews [Method: POST]");
        Integer postUserNo = (Integer) request.getAttribute("userNo");
        Integer userType = (Integer) request.getAttribute("userType");
        if(!userType.equals(1)){
            log.error("권한 없음.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return ResponseEntity.ok(reviewService.createReview(postUserNo, reviewDto));
    }





    @GetMapping
    public List<ReviewBoardEntity> getAllReviews() {
        log.info("request to /api/v1/reviews [Method: GET]");
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ReviewBoardEntity getReviewById(@PathVariable int id) {
        log.info("request to /api/v1/reviews/{id} [Method: GET]");
        return reviewService.getReviewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewBoardEntity> updateReview(@PathVariable int id, @RequestBody ReviewBoardEntity review,HttpServletRequest request) {
        log.info("request to /api/v1/reviews/{id} [Method: PUT]");
        Integer userType = (Integer) request.getAttribute("userType");
        if(!userType.equals(1)){
            log.error("권한 없음.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        review.setReviewNo(id); // Make sure the ID is set based on the path variable
        return ResponseEntity.ok(reviewService.updateReview(review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReturnMessageDto> deleteReview(@PathVariable int id,HttpServletRequest request) {
        log.info("request to /api/v1/reviews/{id} [Method: DELETE]");
        Integer userType = (Integer) request.getAttribute("userType");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if(!userType.equals(1)){
            log.error("권한 없음.");
            returnMessageDto.setMsg("권한 없음.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
        reviewService.deleteReview(id);
        returnMessageDto.setMsg("Deleted review with ID: " + id);
        return ResponseEntity.ok(returnMessageDto);
    }
}
