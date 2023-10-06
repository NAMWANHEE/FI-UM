package com.example.pium.service;

import com.example.pium.dto.CommentDto;
import com.example.pium.entity.ReviewBoardEntity;
import com.example.pium.entity.ReviewCommentEntity;
import com.example.pium.repository.ReviewBoardRepository;
import com.example.pium.repository.ReviewCommentRepository;
import com.example.pium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewBoardRepository reviewBoardRepository;
    private final UserRepository userRepository;

    public List<CommentDto> getComments(Integer reviewNo){
        ReviewBoardEntity reviewBoardEntity = reviewBoardRepository.findById(reviewNo).get();
        List<ReviewCommentEntity> list = reviewCommentRepository.findByReviewNo(reviewBoardEntity);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(ReviewCommentEntity r : list){
            CommentDto commentDto = new CommentDto();
            commentDto.setComment(r.getCommentContent());
            commentDto.setUserName(r.getUserNo().getUserName());
            commentDto.setUserNo(r.getUserNo().getUserNo());
            commentDto.setCreateTime(r.getCreateTime());
            commentDto.setCommentNo(r.getCommentNo());
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }

    public void postComments(Integer reviewNo, String comment, Integer userNo){
        ReviewCommentEntity reviewCommentEntity = ReviewCommentEntity.builder().commentContent(comment).reviewNo(reviewBoardRepository.findById(reviewNo).get()).createTime(BigInteger.valueOf(System.currentTimeMillis())).userNo(userRepository.findByUserNo(userNo).get()).build();
        reviewCommentRepository.save(reviewCommentEntity);
    }
    public boolean putComments(Integer commentNo,String comment,Integer userNo){
        ReviewCommentEntity reviewCommentEntity = reviewCommentRepository.findById(commentNo).get();
        if(!reviewCommentEntity.getUserNo().getUserNo().equals(userNo)) return false;
        reviewCommentEntity.setCommentContent(comment);
        reviewCommentRepository.save(reviewCommentEntity);
        return true;
    }

    public boolean deleteComments(Integer commentNo,Integer userNo, Integer userType){
        ReviewCommentEntity reviewCommentEntity = reviewCommentRepository.findById(commentNo).get();
        if(!userType.equals(1) && !reviewCommentEntity.getUserNo().getUserNo().equals(userNo)) return false;
        reviewCommentRepository.delete(reviewCommentEntity);
        return true;
    }
}
