package com.example.pium.controller;

import com.example.pium.dto.DonationDto;
import com.example.pium.service.DonationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/donation")
@RestController
@Slf4j
public class DonationController {

    private final DonationService donationService;
    @PostMapping
    public ResponseEntity<Map<String,String>> doDonation(HttpServletRequest request, @RequestBody DonationDto donationDto){
        Integer userNo = (Integer) request.getAttribute("userNo");
        boolean check = donationService.donate(userNo, donationDto.getDonationMoney());
        Map<String,String> map = new HashMap<>();
        if(check){
            map.put("msg","기부완료");
            return ResponseEntity.ok(map);
        }
        else{
            map.put("msg","보유한 포인트가 부족합니다.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(map);
        }
    }
}
