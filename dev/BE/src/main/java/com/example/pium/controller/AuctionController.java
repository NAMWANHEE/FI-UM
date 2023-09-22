package com.example.pium.controller;

import com.example.pium.dto.AuctionDto;
import com.example.pium.dto.RGSAuctionDto;
import com.example.pium.dto.ReturnMessageDto;
import com.example.pium.dto.UserAuctionDto;
import com.example.pium.entity.ArtAuctionEntity;
import com.example.pium.entity.UserEntity;
import com.example.pium.service.AuctionServiceImp;
import com.example.pium.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@CrossOrigin(value = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/auction")
@RestController
@Slf4j
public class AuctionController {
    private final AuctionServiceImp auctionService;
    private final UserServiceImp userService;
    @GetMapping
    public ResponseEntity<List<AuctionDto>> getOnGoingList() {
        log.info("request to /api/v1/auction [Method: GET]");
        List<AuctionDto> auctionList = auctionService.makeOnGoingList();
        return ResponseEntity.ok(auctionList);
    }

    @PostMapping
    public ResponseEntity<ReturnMessageDto> postAuction(HttpServletRequest request, @RequestBody RGSAuctionDto rgsAuctionDto) {
        log.info("request to /api/v1/auction [Method: POST]");
        Integer postUser = (Integer) request.getAttribute("userNo");
        Integer userType = (Integer) request.getAttribute("userType");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if(!userType.equals(2)){
            log.error("권한 없음.");
            returnMessageDto.setMsg("권한 없음.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
        Boolean checkAuction = auctionService.postAuction(postUser, rgsAuctionDto);
        if (rgsAuctionDto.getTitle().length() <2 | rgsAuctionDto.getContent().length() < 2) {
            returnMessageDto.setMsg("입력정보를 다시 확인하세요.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.BAD_REQUEST);
        } else {
            if (checkAuction) {
                returnMessageDto.setMsg("경매 등록이 성공적으로 완료되었습니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
            } else  {
                returnMessageDto.setMsg("이미 경매물품이 등록되어있습니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.FORBIDDEN);
            }
        }
    }

    @PutMapping("{auctionNo}")
    public ResponseEntity<ReturnMessageDto> modifyAuctionDetail(@PathVariable("auctionNo") Integer auctionNo, @RequestBody RGSAuctionDto rgsAuctionDto, HttpServletRequest request) {
        Integer userNo = (Integer) request.getAttribute("userNo");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        log.info("request to /api/v1/auction/{auctionNo} [Method: PUT]");
        if(!auctionService.getAuctionInfo(auctionNo).getUserNo().getUserNo().equals(userNo)){
            log.error("권한 없음.");
            returnMessageDto.setMsg("권한 없음.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        };
        if (rgsAuctionDto.getTitle().length() <2 | rgsAuctionDto.getContent().length() < 2) {
            returnMessageDto.setMsg("입력정보를 다시 확인하세요.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.BAD_REQUEST);
        } else {
            ArtAuctionEntity artAuctionEntity = auctionService.getAuctionInfo(auctionNo);
            artAuctionEntity.setTitle(rgsAuctionDto.getTitle());
            artAuctionEntity.setContent(rgsAuctionDto.getContent());
            auctionService.post(artAuctionEntity);
            returnMessageDto.setMsg("경매 수정이 성공적으로 완료되었습니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
        }
    }

    @GetMapping("detail/{auctionNo}")
    public ResponseEntity<AuctionDto> getAuctionDetail(@PathVariable("auctionNo") Integer auctionNo) {
        log.info("request to /api/v1/auction/detail [Method: GET]");
        AuctionDto auctionDetail = auctionService.convertToAuctionDto(auctionNo);
        return ResponseEntity.ok(auctionDetail);
    }

    @PostMapping("bid/{auctionNo}")
    public ResponseEntity<ReturnMessageDto> modifyAuctionPrice(HttpServletRequest request, @PathVariable("auctionNo") Integer auctionNo, @RequestBody RGSAuctionDto rgsAuctionDto) {
        log.info("request to /api/v1/auction/bid/{auctionNo} [Method: POST]");
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        Integer userType = (Integer) request.getAttribute("userType");
        if(userType.equals(2)){
            log.error("권한 없음.");
            returnMessageDto.setMsg("권한 없음");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }
        ArtAuctionEntity artAuctionEntity = auctionService.getAuctionInfo(auctionNo);
        Integer buyer = (Integer) request.getAttribute("userNo");
        Integer seller = artAuctionEntity.getUserNo().getUserNo();
        Integer bidPrice = rgsAuctionDto.getAuctionPrice();
        UserEntity user = userService.getUserInfo(buyer);
        if(user.getPoint() < bidPrice){
            returnMessageDto.setMsg("보유한 금액보다 높은 가격으로 입찰 시도.");
            log.error("보유한 금액보다 높은 가격으로 입찰 시도.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(returnMessageDto);
        }

        // 일단 낙찰자가 있는지 없는지 확인하여 구분 있으면 이미 판매된 상품 메세지
        if (artAuctionEntity.getWinner() == null) {
            // 현재 경매품에 등록된 금액보다 작거나 같으면 경매입찰을 할수 없는 로직 설정
            if (artAuctionEntity.getAuctionPrice() >= rgsAuctionDto.getAuctionPrice()) {
                returnMessageDto.setMsg("현재 금액보다 낮거나 같은 금액으로 입찰 시도하였음.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
            } else {
                // 구매 가능한 경우 입찰 기록에 등록 및 즉시구매가일 경우 낙찰자까지 입력하는 로직
                auctionService.makeRecord(userService.getUserInfo(buyer), artAuctionEntity, rgsAuctionDto);
                if (rgsAuctionDto.getAuctionPrice().equals(artAuctionEntity.getInstantPrice())) {
                    artAuctionEntity.setWinner(userService.getUserInfo(buyer));
                    auctionService.changePoint(userService.getUserInfo(buyer), userService.getUserInfo(seller), rgsAuctionDto.getAuctionPrice());
                    returnMessageDto.setMsg("구매에 성공하였습니다.");
                }
                else {
                    returnMessageDto.setMsg("입찰에 성공하였습니다.");
                }
                artAuctionEntity.setAuctionPrice(rgsAuctionDto.getAuctionPrice());
                auctionService.post(artAuctionEntity);
                return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
            }
        } else {
            returnMessageDto.setMsg("이미 판매 완료된 상품입니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("purchase")
    public ResponseEntity<List<UserAuctionDto>> getPurchaseArt(HttpServletRequest request) {
        Integer userType = (Integer) request.getAttribute("userType");
        if(userType.equals(2)){
            log.error("권한 없음.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        log.info("request to /api/v1/auction/purchase [Method: GET]");
        Integer buyer = (Integer) request.getAttribute("userNo");
        List<UserAuctionDto> purchaseData = auctionService.getPurchaseArt(buyer);
        return new ResponseEntity<>(purchaseData, HttpStatus.OK);
    }
}
