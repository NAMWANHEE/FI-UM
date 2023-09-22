package com.example.pium.controller;

import com.example.pium.entity.UserEntity;
import com.example.pium.service.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ReviewBoardDto createReview(HttpServletRequest request, @RequestBody ReviewBoardDto reviewDto) {
        log.info("request to /api/v1/reviews [Method: POST]");
        Integer postUserNo = (Integer) request.getAttribute("userNo");
        return reviewService.createReview(postUserNo, reviewDto);
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
    public ReviewBoardEntity updateReview(@PathVariable int id, @RequestBody ReviewBoardEntity review) {
        log.info("request to /api/v1/reviews/{id} [Method: PUT]");
        review.setReviewNo(id); // Make sure the ID is set based on the path variable
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable int id) {
        log.info("request to /api/v1/reviews/{id} [Method: DELETE]");
        reviewService.deleteReview(id);
        return "Deleted review with ID: " + id;
    }
}
