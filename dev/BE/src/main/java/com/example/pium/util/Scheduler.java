package com.example.pium.util;

import com.example.pium.dto.projection.*;
import com.example.pium.entity.BalanceSheetEntity;
import com.example.pium.entity.PointRecordEntity;
import com.example.pium.entity.RankingEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final BalanceSheetRepository balanceSheetRepository;
    private final DepositRepository depositRepository;
    private final UserRepository userRepository;
    private final PointRecordRepository pointRecordRepository;
    private final PointTypeRepository pointTypeRepository;
    private final SavingRepository savingRepository;
    private final RankingRepository rankingRepository;

    @Scheduled(cron ="0 0 0 * * *")
    public void deposit(){
        System.out.println("예금 이자 시작");
        // 각 사람별 , 은행별 이자율과 우대 이자율, 현재 예금중인 금액과 24시간이내에 변동된 금액 반환
        List<DepositAll> depositAllList = depositRepository.findAllDeposit(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        for(DepositAll deposit: depositAllList){

            Integer userNo = deposit.getUserNo();
            UserEntity user = userRepository.findByUserNo(userNo).get();
            Integer recentMoney = deposit.getRecentMoney() == null ? 0 : deposit.getRecentMoney();
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

    @Scheduled(cron ="0 0 0 * * *")
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

    @Scheduled(cron ="0 0 0 * * *")
    public void ranking(){
        // 주식왕
        List<Rank> stockRankList = balanceSheetRepository.getStockRanking();
        RankingEntity rankingEntity = rankingRepository.findByRankingType("주식");
        rankingEntity.setRankingNo1(null);rankingEntity.setRankingNo2(null);rankingEntity.setRankingNo3(null);
        Stack<Integer> stack = new Stack<>();
        for(Rank stockRank: stockRankList){
            int rank = stockRank.getRanking();
            int userNo = stockRank.getUserNo();
            int price = stockRank.getPrice();
            if(rank == 1 && price!=0){
                rankingEntity.setRankingNo1(userRepository.findByUserNo(userNo).get());
            }
            else if(rank == 2 && price!=0){
                rankingEntity.setRankingNo2(userRepository.findByUserNo(userNo).get());
            }
            else if(rank == 3 && price!=0){
                rankingEntity.setRankingNo3(userRepository.findByUserNo(userNo).get());
            }
        }
        rankingRepository.save(rankingEntity);
        // 주식 끝

        // 그림 왕

        RankingEntity rankingEntity2 = rankingRepository.findByRankingType("판매");
        rankingEntity2.setRankingNo1(null);rankingEntity2.setRankingNo2(null);rankingEntity2.setRankingNo3(null);
        List<Rank> auctionRankList = balanceSheetRepository.getAuctionRanking();
        for(Rank auctionRank: auctionRankList){
            int rank = auctionRank.getRanking();
            int userNo = auctionRank.getUserNo();
            int price = auctionRank.getPrice();
            if(rank == 1 && price!=0){
                rankingEntity2.setRankingNo1(userRepository.findByUserNo(userNo).get());
            }
            else if(rank == 2 && price!=0){
                rankingEntity2.setRankingNo2(userRepository.findByUserNo(userNo).get());
            }
            else if(rank == 3 && price!=0){
                rankingEntity2.setRankingNo3(userRepository.findByUserNo(userNo).get());
            }
        }
        rankingRepository.save(rankingEntity2);
        // 그림 끝

        // 기부 왕
        RankingEntity rankingEntity3 = rankingRepository.findByRankingType("기부");
        rankingEntity3.setRankingNo1(null);rankingEntity3.setRankingNo2(null);rankingEntity3.setRankingNo3(null);
        List<Rank> donationRankList = balanceSheetRepository.getDonationRanking();
        for(Rank donationRank : donationRankList){
            int rank = donationRank.getRanking();
            int userNo = donationRank.getUserNo();
            int price = donationRank.getPrice();
            if(rank == 1 && price!=0){
                rankingEntity3.setRankingNo1(userRepository.findByUserNo(userNo).get());
            }
            else if(rank == 2 && price!=0){
                rankingEntity3.setRankingNo2(userRepository.findByUserNo(userNo).get());
            }
            else if(rank == 3 && price!=0){
                rankingEntity3.setRankingNo3(userRepository.findByUserNo(userNo).get());
            }
        }
        rankingRepository.save(rankingEntity3);
        // 기부 끝

        // 펀딩 왕
        RankingEntity rankingEntity4 = rankingRepository.findByRankingType("펀딩");
        rankingEntity4.setRankingNo1(null);rankingEntity4.setRankingNo2(null);rankingEntity4.setRankingNo3(null);
        List<Rank> fundingRankList = balanceSheetRepository.getFundingRanking();
        for(Rank fundingRank : fundingRankList){
            int rank = fundingRank.getRanking();
            int userNo = fundingRank.getUserNo();
            int price = fundingRank.getPrice();
            if(rank == 1 && price!=0){
                rankingEntity4.setRankingNo1(userRepository.findByUserNo(userNo).get());
            }
            else if(rank == 2 && price!=0){
                rankingEntity4.setRankingNo2(userRepository.findByUserNo(userNo).get());
            }
            else if(rank == 3 && price!=0){
                rankingEntity4.setRankingNo3(userRepository.findByUserNo(userNo).get());
            }
        }
        rankingRepository.save(rankingEntity4);
        // 펀딩 끝
    }
}
