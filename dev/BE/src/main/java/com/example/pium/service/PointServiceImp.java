package com.example.pium.service;

import com.example.pium.entity.PointRecordEntity;
import com.example.pium.entity.PointTypeEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.PointRecordRepository;
import com.example.pium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class PointServiceImp {
    private final PointRecordRepository pointRecordRepository;
    private final UserRepository userRepository;

    public Boolean makePointRecord(UserEntity user, PointTypeEntity pointType, Integer price){
        PointRecordEntity buyerPointRecord = PointRecordEntity.builder()
                .userNo(user)
                .pointTypeNo(pointType)
                .pointChange(price)
                .changedTime(BigInteger.valueOf(System.currentTimeMillis()))
                .build();
        pointRecordRepository.save(buyerPointRecord);
        return true;
    }

    public Boolean changePointTable(UserEntity user, Integer price) {
        user.setPoint(user.getPoint()-price);
        userRepository.save(user);
        return true;
    }

}
