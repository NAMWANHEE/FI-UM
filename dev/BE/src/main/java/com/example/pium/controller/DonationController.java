package com.example.pium.controller;

import com.example.pium.dto.DonationDto;
import com.example.pium.dto.ReturnMessageDto;
import com.example.pium.service.DonationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/donation")
@RestController
@Slf4j
public class DonationController {

    private final DonationService donationService;

    // 기부하기
    @PostMapping
    public ResponseEntity<ReturnMessageDto> doDonation(HttpServletRequest request, @RequestBody DonationDto donationDto){
        Integer userType = (Integer) request.getAttribute("userType");
        log.info("request to /api/v1/donation [Method: POST]");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        Integer userNo = (Integer) request.getAttribute("userNo");
        if(!userType.equals(2)){
            log.error("권한 없음.");
            returnMessageDto.setMsg("권한 없음.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
        boolean check = donationService.donate(userNo, donationDto.getDonationMoney());
        if(check){

            returnMessageDto.setMsg("기부완료");
            return ResponseEntity.ok(returnMessageDto);
        }
        else{
            returnMessageDto.setMsg("보유한 포인트가 부족합니다.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
    }
}
