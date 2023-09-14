//package com.example.pium.util;
//
//import com.example.pium.dto.projection.DepositInterest;
//import com.example.pium.repository.BalanceSheetRepository;
//import com.example.pium.repository.DepositRepository;
//import com.example.pium.repository.StockDataRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class Scheduler {
//
//    private final BalanceSheetRepository balanceSheetRepository;
//    private final DepositRepository depositRepository;
//
//    @Scheduled(cron ="0 0 0 * * *")
//    public void Deposit(){
//
//        // 24 시간
//        for(DepositInterest d :depositRepository.findChildDeposit(System.currentTimeMillis()-(1000*60*60*24))){
//            System.out.println(d.getUserNo());
//            System.out.println(d.getDepositMoney());
//        }
//    }
//}
