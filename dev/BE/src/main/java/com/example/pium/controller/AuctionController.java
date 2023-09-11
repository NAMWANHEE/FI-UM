package com.example.pium.controller;

import com.example.pium.dto.AuctionDto;
import com.example.pium.dto.RGSAuctionDto;
import com.example.pium.entity.ArtAuctionEntity;
import com.example.pium.entity.BidRecordEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.service.AuctionServiceImp;
import com.example.pium.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/auction")
@RestController
@Slf4j
public class AuctionController {
    private final AuctionServiceImp auctionService;
    private final UserServiceImp userService;
    @GetMapping
    public ResponseEntity<List<AuctionDto>> getOnGoingList() {
        List<AuctionDto> auctionList = auctionService.makeOnGoingList();
        return ResponseEntity.ok(auctionList);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> postAuction(@RequestBody RGSAuctionDto rgsAuctionDto) {
        Integer tmpUser = 1;
        ArtAuctionEntity artAuctionEntity = ArtAuctionEntity.builder()
                .user(userService.getUserInfo(tmpUser))
                .title(rgsAuctionDto.getTitle())
                .content(rgsAuctionDto.getContent())
                .createdTime(BigInteger.valueOf(System.currentTimeMillis()))
                .imagePath(rgsAuctionDto.getImagePath())
                .instantPrice(rgsAuctionDto.getInstantPrice())
                .build();
        System.out.println(artAuctionEntity);
        auctionService.post(artAuctionEntity);
        Map<String, String> returnMsg = new HashMap<>();
        returnMsg.put("msg","경매 등록이 성공적으로 완료되었습니다.");
        return new ResponseEntity<>(returnMsg, HttpStatus.OK);
    }

    @PutMapping("{auctionNo}")
    public ResponseEntity<Map<String, String>> modifyAuctionDetail(@PathVariable("auctionNo") Integer auctionNo, @RequestBody RGSAuctionDto rgsAuctionDto) {
        ArtAuctionEntity artAuctionEntity = auctionService.getAuctionInfo(auctionNo);
        artAuctionEntity.setTitle(rgsAuctionDto.getTitle());
        artAuctionEntity.setContent(rgsAuctionDto.getContent());
        auctionService.post(artAuctionEntity);
        Map<String, String> returnMsg = new HashMap<>();
        returnMsg.put("msg","경매 수정이 성공적으로 완료되었습니다.");
        return new ResponseEntity<>(returnMsg, HttpStatus.OK);
    }

    @GetMapping("detail/{auctionNo}")
    public ResponseEntity<AuctionDto> getAuctionDetail(@PathVariable("auctionNo") Integer auctionNo) {
        AuctionDto auctionDetail = auctionService.convertToAuctionDto(auctionNo);
        return ResponseEntity.ok(auctionDetail);
    }

    @PostMapping("bid/{auctionNo}")
    public ResponseEntity<Map<String, String>> modifyAuctionPrice(@PathVariable("auctionNo") Integer auctionNo, @RequestBody RGSAuctionDto rgsAuctionDto) {
        ArtAuctionEntity artAuctionEntity = auctionService.getAuctionInfo(auctionNo);
        Integer tmpUser = 2;
        Map<String, String> returnMsg = new HashMap<>();
        // 일단 낙찰자가 있는지 없는지 확인하여 구분 있으면 이미 판매된 상품 메세지
        if (artAuctionEntity.getWinner() == null) {
            // 현재 경매품에 등록된 금액보다 작거나 같으면 경매입찰을 할수 없는 로직 설정
            if (artAuctionEntity.getAuctionPrice() >= rgsAuctionDto.getAuctionPrice()) {
                returnMsg.put("msg","현재 금액보다 낮거나 같은 금액으로 입찰 시도하였음.");
                return new ResponseEntity<>(returnMsg, HttpStatus.NOT_ACCEPTABLE);
            } else {
                // 구매 가능한 경우 입찰 기록에 등록 및 즉시구매가일 경우 낙찰자까지 입력하는 로직
                auctionService.makeRecord(userService.getUserInfo(tmpUser), artAuctionEntity, rgsAuctionDto);

                if (rgsAuctionDto.getAuctionPrice().equals(artAuctionEntity.getInstantPrice())) {
                    artAuctionEntity.setWinner(userService.getUserInfo(tmpUser));
                    returnMsg.put("msg","구매에 성공하였습니다.");
                }
                else {
                    returnMsg.put("msg","입찰에 성공하였습니다.");
                }

                artAuctionEntity.setAuctionPrice(rgsAuctionDto.getAuctionPrice());
                auctionService.post(artAuctionEntity);
                return new ResponseEntity<>(returnMsg, HttpStatus.OK);
            }
        } else {
            returnMsg.put("msg","이미 판매 완료된 상품입니다.");
            return new ResponseEntity<>(returnMsg, HttpStatus.PRECONDITION_FAILED);
        }
    }
}
