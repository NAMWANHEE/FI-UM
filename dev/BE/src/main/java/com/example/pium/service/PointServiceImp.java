package com.example.pium.service;

import com.example.pium.dto.projection.ChildPointInterface;
import com.example.pium.entity.PointRecordEntity;
import com.example.pium.entity.PointTypeEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.PointRecordRepository;
import com.example.pium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImp {
    private final PointRecordRepository pointRecordRepository;
    private final UserRepository userRepository;

    public void makePointRecord(UserEntity user, PointTypeEntity pointType, Integer price){
        PointRecordEntity buyerPointRecord = PointRecordEntity.builder()
                .userNo(user)
                .pointTypeNo(pointType)
                .pointChange(price)
                .changedTime(BigInteger.valueOf(System.currentTimeMillis()))
                .build();
        pointRecordRepository.save(buyerPointRecord);
    }

    public void changePointTable(UserEntity user, Integer price) {
        user.setPoint(user.getPoint()-price);
        userRepository.save(user);
    }

    public void changeCashTable(UserEntity user, Integer money) {
        user.setCash(user.getCash() - money);
        user.setPoint(user.getPoint() + (money/10));
        userRepository.save(user);
    }
    public List<ChildPointInterface> getPointRecord(Integer userNo){
        return pointRecordRepository.findByUserNo(userNo);
    }

}
