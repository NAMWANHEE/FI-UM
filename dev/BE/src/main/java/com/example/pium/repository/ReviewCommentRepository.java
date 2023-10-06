package com.example.pium.repository;

import com.example.pium.dto.CommentDto;
import com.example.pium.entity.ReviewBoardEntity;
import com.example.pium.entity.ReviewCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewCommentRepository extends JpaRepository<ReviewCommentEntity,Integer> {

    List<ReviewCommentEntity> findByReviewNo(ReviewBoardEntity reviewNo);
}
