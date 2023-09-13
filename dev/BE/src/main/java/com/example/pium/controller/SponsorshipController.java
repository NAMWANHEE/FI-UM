package com.example.pium.controller;

import com.example.pium.dto.ItemListDto;
import com.example.pium.dto.ItemRecordDto;
import com.example.pium.dto.MoneyDto;
import com.example.pium.dto.NewItemDto;
import com.example.pium.service.SponsorShipServiceImp;
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

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/sponsorship")
@RestController
@Slf4j
public class SponsorshipController {
    private final SponsorShipServiceImp sponsorShipService;
    private final UserServiceImp userService;

    @GetMapping
    public ResponseEntity<List<ItemListDto>> getAllItemList() {
        List<ItemListDto> allItemList = sponsorShipService.getAllList();
        return new ResponseEntity<>(allItemList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> makeNewItem(HttpServletRequest request, @RequestBody NewItemDto postInformation) {
        Integer teachUser = (Integer) request.getAttribute("userNo");
        Map<String, String> returnMsg = new HashMap<>();
        if (userService.getUserInfo(teachUser).getUserType() == 0) {
            sponsorShipService.makeNewItem(postInformation);
            returnMsg.put("msg","정상적으로 등록되였습니다.");
            System.out.println(1);
            return new ResponseEntity<>(returnMsg, HttpStatus.OK);
        } else {
            returnMsg.put("msg","등록가능한 대상이 아닙니다.");
            return new ResponseEntity<>(returnMsg, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{itemNo}")
    public ResponseEntity<Map<String, String>> changeItemDetail(HttpServletRequest request,@PathVariable("itemNo") Integer itemNo, @RequestBody NewItemDto postInformation) {
        Integer teachUser = (Integer) request.getAttribute("userNo");
        Map<String, String> returnMsg = new HashMap<>();
        if (userService.getUserInfo(teachUser).getUserType().equals(0)) {
            sponsorShipService.changeItem(postInformation, itemNo);
            returnMsg.put("msg","정상적으로 수정되였습니다.");
            return new ResponseEntity<>(returnMsg, HttpStatus.OK);
        } else {
            returnMsg.put("msg","등록가능한 대상이 아닙니다.");
            return new ResponseEntity<>(returnMsg, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("support/{itemNo}")
    public ResponseEntity<Map<String, String>> postSupport(HttpServletRequest request, @PathVariable("itemNo") Integer itemNo, @RequestBody MoneyDto supportPrice) {
        Integer postUser = (Integer) request.getAttribute("userNo");
        Boolean checkPrice = sponsorShipService.checkPrice(itemNo, supportPrice.getMoney());
        Map<String, String> returnMsg = new HashMap<>();
        if (checkPrice) {
            if (userService.checkValidCash(postUser, supportPrice.getMoney())){
                sponsorShipService.postSupport(postUser, itemNo, supportPrice.getMoney());
                returnMsg.put("msg","정상적으로 후원하였습니다.");
                return new ResponseEntity<>(returnMsg, HttpStatus.OK);
            } else {
                returnMsg.put("msg","보유 캐시가 부족합니다.");
                return new ResponseEntity<>(returnMsg, HttpStatus.FAILED_DEPENDENCY);
            }
        } else {
            returnMsg.put("msg","후원가능금액보다 더 많이 후원시도하였습니다.");
            return new ResponseEntity<>(returnMsg, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("record")
    public ResponseEntity<List<ItemRecordDto>> getAllRecord(HttpServletRequest request) {
        Integer teachUser = (Integer) request.getAttribute("userNo");
        if (userService.getUserInfo(teachUser).getUserType().equals(0)) {
            List<ItemRecordDto> allRecord =  sponsorShipService.getAllRecord();
            return new ResponseEntity<>(allRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("record/detail")
    public  ResponseEntity<List<ItemRecordDto>> getRecordDetail(HttpServletRequest request) {
        Integer supportUser = (Integer) request.getAttribute("userNo");
            List<ItemRecordDto> allRecord =  sponsorShipService.getRecordDetail(supportUser);
            return new ResponseEntity<>(allRecord, HttpStatus.OK);
    }


}
