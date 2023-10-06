package com.example.pium.service;

import com.example.pium.dto.ReviewBoardDto;
import com.example.pium.entity.ReviewBoardEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.ReviewBoardRepository;
import com.example.pium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ReviewBoardServiceImp implements ReviewBoardService {

    @Autowired
    private ReviewBoardRepository reviewBoardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ReviewBoardDto createReview(Integer postUserNo, ReviewBoardDto reviewDto) {
        UserEntity user = userRepository.findById(postUserNo).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found for ID: " + postUserNo);
        }

        // Dto to Entity 변환
        ReviewBoardEntity review = new ReviewBoardEntity();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setUserNo(user);
        review.setImagePath(reviewDto.getImagePath());
        review.setCreateTime(BigInteger.valueOf(System.currentTimeMillis()));
        // 기타 필요한 필드들도 여기서 변환

        ReviewBoardEntity savedEntity = reviewBoardRepository.save(review);
        return convertToDto(savedEntity);  // Entity를 Dto로 변환하여 반환
    }

    private ReviewBoardDto convertToDto(ReviewBoardEntity entity) {
        ReviewBoardDto dto = new ReviewBoardDto();
        dto.setReviewNo(entity.getReviewNo());
        dto.setUserNo(entity.getUserNo().getUserNo());  // UserEntity에서 userNo 가져오기
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setImagePath(entity.getImagePath());
        dto.setCreateTime(entity.getCreateTime());
        // 기타 필드들 설정 (예: dto.setOtherField(entity.getOtherField());)

        return dto;
    }

    @Override
    public List<ReviewBoardEntity> getAllReviews() {
        return reviewBoardRepository.findAll();
    }

    @Override
    public ReviewBoardEntity getReviewById(int id) {
        return reviewBoardRepository.findById(id).orElse(null);
    }

    @Override
    public ReviewBoardEntity updateReview(ReviewBoardEntity review) {
        // 존재하는 리뷰인지 확인하고 업데이트
        if (reviewBoardRepository.existsById(review.getReviewNo())) {
            return reviewBoardRepository.save(review);
        } else {
            throw new RuntimeException("Review not found for ID: " + review.getReviewNo());
        }
    }

    @Override
    public void deleteReview(int id) {
        reviewBoardRepository.deleteById(id);
    }
}
