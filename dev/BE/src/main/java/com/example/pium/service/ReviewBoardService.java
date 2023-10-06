package com.example.pium.service;

import com.example.pium.dto.ReviewBoardDto;
import com.example.pium.entity.ReviewBoardEntity;
import java.util.List;

public interface ReviewBoardService {

    ReviewBoardDto createReview(Integer postUser, ReviewBoardDto review);


    List<ReviewBoardEntity> getAllReviews();

    ReviewBoardEntity getReviewById(int id);

    ReviewBoardEntity updateReview(ReviewBoardEntity review);

    void deleteReview(int id);
}
