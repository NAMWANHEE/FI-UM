package com.example.pium.util;

import com.example.pium.dto.projection.DepositAll;
import com.example.pium.dto.projection.DepositInterest;
import com.example.pium.dto.projection.SavingAll;
import com.example.pium.entity.BalanceSheetEntity;
import com.example.pium.entity.PointRecordEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final BalanceSheetRepository balanceSheetRepository;
    private final DepositRepository depositRepository;
    private final UserRepository userRepository;
    private final PointRecordRepository pointRecordRepository;
    private final PointTypeRepository pointTypeRepository;
    private final SavingRepository savingRepository;

    @Scheduled(cron ="0 0 0 * * *")
    public void deposit(){
        System.out.println("예금 이자 시작");
        System.out.println(System.currentTimeMillis());
        System.out.println();
        // 각 사람별 , 은행별 이자율과 우대 이자율, 현재 예금중인 금액과 24시간이내에 변동된 금액 반환
        List<DepositAll> depositAllList = depositRepository.findAllDeposit(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        for(DepositAll deposit: depositAllList){
            System.out.println(deposit.getBankName());
            System.out.println(deposit.getInterestRate());
            System.out.println(deposit.getPrimeInterestRate());
            System.out.println(deposit.getUserNo());
            System.out.println(deposit.getTotalMoney());
            Integer userNo = deposit.getUserNo();
            UserEntity user = userRepository.findByUserNo(userNo).get();
            Integer recentMoney = deposit.getRecentMoney() == null ? 0 : deposit.getRecentMoney();
            System.out.println(recentMoney);
            if(deposit.getTotalMoney() == 0) continue; // 현재 예금 금액이 0원인 경우 패스
            Integer interest;
            if(deposit.getBankName().equals("국민은행")){
                if(user.getIsPrimed2()){ // 국민은행의 우대이자를 받는 경우
                    if(recentMoney < 0){ // 24 시간 이내에 출금한 돈이 더 많은 경우 그냥 현재 예금 금액에 이자+우대이자 해서 지급
                        interest = (int)(deposit.getTotalMoney() * ((deposit.getInterestRate()+deposit.getPrimeInterestRate())/100.0));
                    }
                    else {  // 24시간 내에 입금한 금액이 있는 경우 total 에서 recent 를 뺀 금액에 대해 이자 지급
                        interest = (int)((deposit.getTotalMoney()-recentMoney) * ((deposit.getInterestRate()+deposit.getPrimeInterestRate())/100.0));
                    }
                }
                else{  // 국민은행의 우대이자를 받지 않는 경우
                    if(recentMoney < 0){
                        interest = (int)(deposit.getTotalMoney() * (deposit.getInterestRate()/100.0));
                    }
                    else{
                        interest = (int)((deposit.getTotalMoney()-recentMoney) * (deposit.getInterestRate()/100.0));
                    }

                }
            }
            else{
                if(user.getIsPrimed2()){ // 신한은행의 우대이자를 받는 경우
                    if(recentMoney < 0){ // 24 시간 이내에 출금한 돈이 더 많은 경우 그냥 현재 예금 금액에 이자+우대이자 해서 지급
                        interest = (int)(deposit.getTotalMoney() * ((deposit.getInterestRate()+deposit.getPrimeInterestRate())/100.0));
                    }
                    else {  // 24시간 내에 입금한 금액이 있는 경우 total 에서 recent 를 뺀 금액에 대해 이자 지급
                        interest = (int)((deposit.getTotalMoney()-recentMoney) * ((deposit.getInterestRate()+deposit.getPrimeInterestRate())/100.0));
                    }
                }
                else{  // 국민은행의 우대이자를 받지 않는 경우
                    if(recentMoney < 0){
                        interest =(int)( deposit.getTotalMoney() * (deposit.getInterestRate()/100.0));
                    }
                    else{
                        interest = (int)((deposit.getTotalMoney()-recentMoney) * (deposit.getInterestRate()/100.0));
                    }

                }
            }
            System.out.println(interest);
            // 재무상태표 업데이트
            BalanceSheetEntity balanceSheet = balanceSheetRepository.findByUserNo(user).get();
            balanceSheet.setDepositIncome(interest);
            balanceSheet.setPoint(balanceSheet.getPoint()+interest);
            balanceSheetRepository.save(balanceSheet);
            //
            // 유저테이블 포인트 업데이트
            user.setPoint(user.getPoint()+interest);
            userRepository.save(user);
            //
            // 포인트사용내역 업데이트
            PointRecordEntity pointRecordEntity = PointRecordEntity.builder().userNo(user).pointChange(interest).pointTypeNo(pointTypeRepository.findByPointType("예금이자").get()).changedTime(BigInteger.valueOf(System.currentTimeMillis())).build();
            pointRecordRepository.save(pointRecordEntity);
            //
        }
    }

    @Scheduled(cron ="0 * * * * *")
    public void saving(){
        List<SavingAll> savingAllList = savingRepository.findAllSaving(); // 모든 적금 리스트 => 적금금액, 적금일, 고객, 이자율, 우대이자율, 은행명
        for(SavingAll savingAll : savingAllList){
            if(savingAll.getCreateSaving().longValueExact() >= System.currentTimeMillis()-(1000*60*60*24*7)) continue;
            UserEntity user = userRepository.findByUserNo(savingAll.getUserNo()).get();
            Integer interest;
            if(savingAll.getBankName().equals("국민은행")){
                if(user.getIsPrimed2()){
                    interest = (int)(savingAll.getSavingBalance() * ((savingAll.getInterestRate()+savingAll.getPrimeInterestRate())/100.0));
                }
                else{
                    interest = (int)(savingAll.getSavingBalance() * (savingAll.getInterestRate()/100.0));
                }
            }
            else{
                if(user.getIsPrimed1()){
                    interest = (int)(savingAll.getSavingBalance() * ((savingAll.getInterestRate()+savingAll.getPrimeInterestRate())/100.0));
                }
                else{
                    interest = (int)(savingAll.getSavingBalance() * (savingAll.getInterestRate()/100.0));
                }
            }
            Integer savingMoney = savingAll.getSavingBalance();
            // 재무상태표 업데이트
            BalanceSheetEntity balanceSheet = balanceSheetRepository.findByUserNo(user).get();
            balanceSheet.setSavingIncome(interest);
            balanceSheet.setPoint(balanceSheet.getPoint()+interest+savingMoney);
            balanceSheet.setSaving(0);
            balanceSheetRepository.save(balanceSheet);
            //
            // 유저테이블 포인트 업데이트
            user.setPoint(user.getPoint()+interest+savingMoney);
            userRepository.save(user);
            //
            // 포인트사용내역 업데이트
            PointRecordEntity pointRecordEntity = PointRecordEntity.builder().userNo(user).pointChange(interest).pointTypeNo(pointTypeRepository.findByPointType("적금이자").get()).changedTime(BigInteger.valueOf(System.currentTimeMillis())).build();
            pointRecordRepository.save(pointRecordEntity);
            PointRecordEntity pointRecordEntity2 = PointRecordEntity.builder().userNo(user).pointChange(savingMoney).pointTypeNo(pointTypeRepository.findByPointType("적금").get()).changedTime(BigInteger.valueOf(System.currentTimeMillis())).build();
            pointRecordRepository.save(pointRecordEntity2);
            //
            // 적금 만료 => 테이블에서 삭제
            savingRepository.deleteById(savingAll.getSavingNo());
            //
        }
    }

//    @Scheduled(cron ="0 0 0 * * *")
//    public void ranking(){
//
//    }
}
