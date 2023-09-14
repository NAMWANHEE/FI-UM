package com.example.pium.service;

import com.example.pium.dto.ChildUserDto;
import com.example.pium.dto.ReturnMessageDto;
import com.example.pium.dto.projection.ChildUserInterface;
import com.example.pium.entity.FollowEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.FollowRepository;
import com.example.pium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public ReturnMessageDto doFollowing(int userNo, int followingUserNo){

        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        UserEntity following =  userRepository.findByUserNo(userNo).get();
        UserEntity follower = userRepository.findByUserNo(followingUserNo).get();
        if(!followRepository.findByFollowingAndFollower(following,follower).isPresent()){
            returnMessageDto.setMsg("팔로우 성공");
            FollowEntity followEntity = FollowEntity.builder().following(userRepository.findByUserNo(userNo).get()).follower(userRepository.findByUserNo(followingUserNo).get()).build();
            followRepository.save(followEntity);
        }
        else{
            returnMessageDto.setMsg("팔로우 취소");
            int followNo = followRepository.findByFollowingAndFollower(following,follower).get().getFollowNo();
            followRepository.deleteById(followNo);
        }
        return returnMessageDto;
    }

    public List<ChildUserInterface> getFollowing(Integer userNo){
        return followRepository.findByUserNo(userRepository.findByUserNo(userNo).get());
    }
}
