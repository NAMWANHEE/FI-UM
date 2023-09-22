package com.example.pium.controller;

import com.example.pium.dto.DepositAccountDto;
import com.example.pium.dto.MoneyDto;
import com.example.pium.dto.ReturnMessageDto;
import com.example.pium.entity.UserEntity;
import com.example.pium.service.BankServiceImp;
import com.example.pium.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/bank")
@RestController
@Slf4j
public class BankController {
    private final BankServiceImp bankService;
    private final UserServiceImp userService;

    @PostMapping("saving")
    public ResponseEntity<ReturnMessageDto> makeSaving(HttpServletRequest request, @RequestParam String option, @RequestBody MoneyDto savingMoney) {
        log.info("request to /api/v1/bank/saving [Method: POST]");
        Integer postUser = (Integer) request.getAttribute("userNo");
        Boolean checkSavingAccount = bankService.checkSaving(postUser, option);

        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        // 이미 적금이 가입되어 있는지 여부 파악
        if (checkSavingAccount) {
            returnMessageDto.setMsg("이미 해당 은행 계좌가 존재합니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
        } else {
            // 보유한 포인트가 적금을 들수 있는 금액인지 여부 파악
            if (userService.checkValidPoint(postUser, savingMoney.getMoney())) {
                bankService.makeSaving(postUser, option, savingMoney.getMoney());
                returnMessageDto.setMsg("적금 처리가 완료되었습니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
            } else {
                returnMessageDto.setMsg("보유 포인트가 부족합니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("deposit")
    public ResponseEntity<ReturnMessageDto> makeDeposit(HttpServletRequest request, @RequestParam String option, @RequestBody MoneyDto depositMoney) {
        log.info("request to /api/v1/bank/deposit [Method: POST]");
        Integer postUser = (Integer) request.getAttribute("userNo");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        // 보유 포인트가 예금을 더 넣을 수 있는 금액인지 여부 파악 (인출이여도 상관없음)
        if (userService.checkValidPoint(postUser, depositMoney.getMoney())) {
            bankService.makeDeposit(postUser, option, depositMoney.getMoney());
            returnMessageDto.setMsg("예금 입출금처리가 되었습니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
        } else {
            returnMessageDto.setMsg("보유 포인트가 부족합니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("deposit")
    public ResponseEntity<List<DepositAccountDto>> getDeposit(HttpServletRequest request) {
        log.info("request to /api/v1/bank/deposit [Method: GET]");
        Integer postUser = (Integer) request.getAttribute("userNo");
        List<DepositAccountDto> depositList = bankService.getDepositList(postUser);
        return new ResponseEntity<>(depositList, HttpStatus.OK);
    }

    @GetMapping("checkPrime")
    public ResponseEntity<ReturnMessageDto> checkPrime(HttpServletRequest request, @RequestParam String option) {
        log.info("request to /api/v1/bank/checkPrime [Method: GET]");
        Integer checkUser = (Integer) request.getAttribute("userNo");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        UserEntity userData = userService.getUserInfo(checkUser);
        if (bankService.checkBank(option).getBankNo().equals(1)) {
            if (userData.getIsPrimed1()) {
                returnMessageDto.setMsg("우대조건을 만족합니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
            } else {
                returnMessageDto.setMsg("우대조건을 불만족합니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            if (userData.getIsPrimed2()) {
                returnMessageDto.setMsg("우대조건을 만족합니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
            } else {
                returnMessageDto.setMsg("우대조건을 불만족합니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }
}