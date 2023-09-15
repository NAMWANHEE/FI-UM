package com.example.pium.controller;

import com.example.pium.dto.*;
import com.example.pium.service.FundingServiceImp;
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
@RequestMapping("/funding")
@RestController
@Slf4j
public class FundingController {
    private final FundingServiceImp fundingService;
    private final UserServiceImp userService;

    @GetMapping
    public ResponseEntity<List<MyFundingDto>> getFunding() {
        List<MyFundingDto> getProgress = fundingService.getFunding();
        return new ResponseEntity<>(getProgress, HttpStatus.OK);
    }

    @PostMapping("{itemNo}")
    public ResponseEntity<ReturnMessageDto> postFunding(HttpServletRequest request, @PathVariable("itemNo") Integer itemNo, @RequestBody MoneyDto money) {
        Integer childUser = (Integer) request.getAttribute("userNo");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if (fundingService.checkFundingNow(itemNo, money.getMoney())) {
            if (userService.checkValidPoint(childUser, money.getMoney())) {
                if (userService.getUserInfo(childUser).getUserType() == 2) {
                    fundingService.postFunding(childUser, itemNo, money.getMoney());
                    returnMessageDto.setMsg("정상적으로 펀딩되였습니다.");
                    return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
                } else {
                    returnMessageDto.setMsg("펀딩가능한 대상이 아닙니다.");
                    return new ResponseEntity<>(returnMessageDto, HttpStatus.FAILED_DEPENDENCY);
                }
            } else {
                returnMessageDto.setMsg("보유 포인트가 부족합니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.BAD_REQUEST);
            }
        } else  {
            returnMessageDto.setMsg("남은 펀딩금액보다 펀딩금액이 큽니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("progress")
    public ResponseEntity<FundingProgressDto> getFundingProgress(HttpServletRequest request) {
        Integer teachUser = (Integer) request.getAttribute("userNo");
        if (userService.getUserInfo(teachUser).getUserType() == 0) {
            FundingProgressDto getProgress = fundingService.getFundingProgress();
            return new ResponseEntity<>(getProgress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("myFunding")
    public ResponseEntity<List<MyFundingDto>> getMyFunding(HttpServletRequest request) {
        Integer childUser = (Integer) request.getAttribute("userNo");
        List<MyFundingDto> getProgress = fundingService.getMyFunding(childUser);
        return new ResponseEntity<>(getProgress, HttpStatus.OK);
    }
}
