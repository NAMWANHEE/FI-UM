package com.example.pium.service;

import com.example.pium.dto.DepositAccountDto;
import com.example.pium.dto.projection.BankInfo;
import com.example.pium.entity.*;
import com.example.pium.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankServiceImp {
    private final BankListRepository bankListRepository;
    private final ProductTypeRepository productTypeRepository;
    private final DepositRepository depositRepository;
    private final SavingRepository savingRepository;
    private final BankProductDataRepository bankProductDataRepository;
    private final UserServiceImp userService;
    private final PointServiceImp pointService;
    private final PointTypeRepository pointTypeRepository;
    private final BalanceSheetRepository balanceSheetRepository;

    public BankListEntity checkBank(String bankName) {
        BankListEntity bank = bankListRepository.findByBankName(bankName);
        return bank;
    }

    public BankProductDataEntity checkBankProduct(String productType, String bankName){
        BankListEntity bank = checkBank(bankName);
        ProductTypeEntity product = productTypeRepository.findByType(productType);
        BankProductDataEntity productNo = bankProductDataRepository.findByBankNoAndProductNo(bank, product);
        return productNo;
    }


    public Boolean checkSaving(Integer user, String bankName) {
        BankProductDataEntity productNo = checkBankProduct("적금", bankName);
        Optional<SavingEntity> isSavingExist = savingRepository.findByUserNoAndBankProductDataNo(userService.getUserInfo(user), productNo);
        if (!isSavingExist.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public void makeSaving(Integer user, String bankName, Integer savingMoney) {
        BankProductDataEntity productData = checkBankProduct("적금", bankName);
        UserEntity userData = userService.getUserInfo(user);
        // 적금 테이블에 추가
        SavingEntity createSaving = SavingEntity.builder()
                .userNo(userData)
                .savingBalance(savingMoney)
                .bankProductDataNo(productData)
                .createSaving(BigInteger.valueOf(System.currentTimeMillis()))
                .build();
        savingRepository.save(createSaving);
        // 포인트 수정
        pointService.changePointTable(userData, savingMoney);
        // 포인트 변동내역 추가
        PointTypeEntity pointType = pointTypeRepository.findByPointType("적금").get();
        pointService.makePointRecord(userData, pointType, -savingMoney);
        // 재무상태표 변동
        BalanceSheetEntity sellerBalance = balanceSheetRepository.findByUserNo(userData).get();
        sellerBalance.setSaving(savingMoney);
        sellerBalance.setPoint(sellerBalance.getPoint()-savingMoney);
        balanceSheetRepository.save(sellerBalance);
    }

    public void makeDeposit(Integer user, String bankName, Integer depositMoney){
        BankProductDataEntity productData = checkBankProduct("예금", bankName);
        UserEntity userData = userService.getUserInfo(user);
        BalanceSheetEntity sellerBalance = balanceSheetRepository.findByUserNo(userData).get();
        // 예금 내역 추가
        DepositEntity createDeposit = DepositEntity.builder()
                .userNo(userData)
                .bankProductDataNo(productData)
                .depositMoney(depositMoney)
                .depositBalance(sellerBalance.getDeposit()+depositMoney)
                .createDeposit(BigInteger.valueOf(System.currentTimeMillis()))
                .build();
        depositRepository.save(createDeposit);
        // 재무상태표에 반영
        sellerBalance.setDeposit(sellerBalance.getDeposit()+depositMoney);
        sellerBalance.setPoint(sellerBalance.getPoint()-depositMoney);
        balanceSheetRepository.save(sellerBalance);
        // 포인트 수정
        pointService.changePointTable(userData, depositMoney);
        // 포인트 변동내역 추가
        PointTypeEntity pointType = pointTypeRepository.findByPointType("예금").get();
        pointService.makePointRecord(userData, pointType, -depositMoney);
    }

    public List<DepositAccountDto> getDepositList(Integer user) {
        UserEntity checkUser = userService.getUserInfo(user);
        List<DepositEntity> getList = depositRepository.findAllByUserNo(checkUser);
        List<DepositAccountDto> depositList = new ArrayList<>();
        for (DepositEntity depositDetail : getList) {
            DepositAccountDto tmpDto = new DepositAccountDto();
            tmpDto.setBankNo(depositDetail.getBankProductDataNo().getBankNo().getBankNo());
            tmpDto.setBankName(depositDetail.getBankProductDataNo().getBankNo().getBankName());
            tmpDto.setDepositBalance(depositDetail.getDepositBalance());
            tmpDto.setCreateDeposit(depositDetail.getCreateDeposit());
            tmpDto.setDepositMoney(depositDetail.getDepositMoney());
            depositList.add(tmpDto);
        }
        return depositList;
    }

    public List<BankInfo> getBankInfo(){
        return bankProductDataRepository.getBankInfo();
    }
}
