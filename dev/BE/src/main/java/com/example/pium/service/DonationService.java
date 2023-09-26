package com.example.pium.service;

import com.example.pium.entity.BalanceSheetEntity;
import com.example.pium.entity.PointTypeEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.BalanceSheetRepository;
import com.example.pium.repository.PointRecordRepository;
import com.example.pium.repository.PointTypeRepository;
import com.example.pium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final BalanceSheetRepository balanceSheetRepository;
    private final UserRepository userRepository;
    private final PointServiceImp pointService;
    private  final PointTypeRepository pointTypeRepository;

    public boolean donate(Integer userNo, Integer money){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        BalanceSheetEntity balanceSheetEntity = balanceSheetRepository.findByUserNo(user);
        PointTypeEntity pointType = pointTypeRepository.findByPointType("기부").get();
        if(user.getPoint() >= money){
            user.setPoint(user.getPoint()-money);
            userRepository.save(user);
            balanceSheetEntity.setPoint(balanceSheetEntity.getPoint() - money);
            balanceSheetEntity.setDonation(balanceSheetEntity.getDonation() + money);
            balanceSheetRepository.save(balanceSheetEntity);
            pointService.makePointRecord(user,pointType,money);
            return true;
        }
        else{
            return false;
        }

    }

    public void checkDonation(Integer userNo){
        UserEntity user = userRepository.findByUserNo(userNo).get();
        if(user.getIsPrimed1()) return; // 이미 우대이자를 받는 경우 패스
        if(balanceSheetRepository.findByUserNo(user).get().getDonation()>=100000){
            user.setIsPrimed1(true);
            userRepository.save(user);
        }
    }
}
