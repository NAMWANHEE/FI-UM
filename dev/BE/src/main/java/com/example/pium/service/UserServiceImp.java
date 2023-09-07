package com.example.pium.service;

import com.example.pium.entity.UserEntity;
import com.example.pium.repositiory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp {
    private final UserRepository userRepository;

    public void save(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    public boolean isUserIdExist(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }
}

