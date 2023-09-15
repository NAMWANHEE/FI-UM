package com.example.pium.controller;

import com.example.pium.dto.*;
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

@CrossOrigin(value = "*", allowedHeaders = "*")
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
    public ResponseEntity<ReturnMessageDto> makeNewItem(HttpServletRequest request, @RequestBody NewItemDto postInformation) {
        Integer teachUser = (Integer) request.getAttribute("userNo");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if (userService.getUserInfo(teachUser).getUserType() == 0) {
            sponsorShipService.makeNewItem(postInformation);
            returnMessageDto.setMsg("정상적으로 등록되였습니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
        } else {
            returnMessageDto.setMsg("등록가능한 대상이 아닙니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{itemNo}")
    public ResponseEntity<ReturnMessageDto> changeItemDetail(HttpServletRequest request,@PathVariable("itemNo") Integer itemNo, @RequestBody NewItemDto postInformation) {
        Integer teachUser = (Integer) request.getAttribute("userNo");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if (userService.getUserInfo(teachUser).getUserType().equals(0)) {
            sponsorShipService.changeItem(postInformation, itemNo);
            returnMessageDto.setMsg("정상적으로 수정되였습니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
        } else {
            returnMessageDto.setMsg("등록가능한 대상이 아닙니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("support/{itemNo}")
    public ResponseEntity<ReturnMessageDto> postSupport(HttpServletRequest request, @PathVariable("itemNo") Integer itemNo, @RequestBody MoneyDto supportPrice) {
        Integer postUser = (Integer) request.getAttribute("userNo");
        Boolean checkPrice = sponsorShipService.checkPrice(itemNo, supportPrice.getMoney());
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if (checkPrice) {
            if (userService.checkValidCash(postUser, supportPrice.getMoney())){
                sponsorShipService.postSupport(postUser, itemNo, supportPrice.getMoney());
                returnMessageDto.setMsg("정상적으로 후원하였습니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
            } else {
                returnMessageDto.setMsg("보유 캐시가 부족합니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
            }
        } else {
            returnMessageDto.setMsg("후원가능금액보다 더 많이 후원시도하였습니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.BAD_REQUEST);
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
