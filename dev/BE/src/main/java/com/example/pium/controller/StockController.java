package com.example.pium.controller;

import com.example.pium.dto.*;
import com.example.pium.service.RankingServiceImp;
import com.example.pium.service.StockServiceImp;
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
@RequestMapping("/stock")
@RestController
@Slf4j
public class StockController {
    private final StockServiceImp stockService;
    private final RankingServiceImp rankingService;
    private final UserServiceImp userService;

    @GetMapping
    public List<StockDataDto> getAllStockData() {
        List<StockDataDto> stockDataDto = stockService.getAllData();
        return stockDataDto;
    }

    @GetMapping("chart/{stockNo}")
    public List<StockDataDto> getStockDetail(@PathVariable("stockNo") Integer stockNo) {
        List<StockDataDto> stockDetailDto = stockService.getDetailData(stockNo);
        return stockDetailDto;
    }

    @GetMapping("my-account/{stockNo}")
    public StockAccountDto getDetailStockAccount(HttpServletRequest request, @PathVariable("stockNo") Integer stockNo) {
        log.info("요청왔다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info(String.valueOf(stockNo));
        Integer myUser = (Integer) request.getAttribute("userNo");
        StockAccountDto myAccountDetail = stockService.getDetailAccount(stockNo, myUser);
        return myAccountDetail;
    }

    @PostMapping("buying")
    public ResponseEntity<ReturnMessageDto> buyStock(HttpServletRequest request, @RequestBody StockTradeDto stockTradeDto) {
        Integer buyUser = (Integer) request.getAttribute("userNo");
        Boolean checkPrice = stockService.getStockNow(stockTradeDto.getStockNo(), stockTradeDto.getPrice());
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        // 현재 금액이 구매하려고 하는 금액과 일치하는지 여부
        if (checkPrice) {
            Integer price = stockTradeDto.getPrice() * stockTradeDto.getCount();
            // 보유포인트가 구매 가능한 정도로 남았는지 체크
            if (userService.checkValidPoint(buyUser, price)) {
                stockService.buyStock(stockTradeDto, buyUser);
                returnMessageDto.setMsg("구매가 완료되었습니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
            } else {
                returnMessageDto.setMsg("보유 포인트가 부족합니다.");
                return new ResponseEntity<>(returnMessageDto, HttpStatus.FAILED_DEPENDENCY);
            }
        } else {
            returnMessageDto.setMsg("구매금액을 다시 확인해주시기 바랍니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("selling")
    public ResponseEntity<ReturnMessageDto> sellStock(HttpServletRequest request, @RequestBody StockTradeDto stockTradeDto) {
        Integer sellUser = (Integer) request.getAttribute("userNo");
        Boolean checkPrice = stockService.getStockNow(stockTradeDto.getStockNo(), stockTradeDto.getPrice());
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        if (checkPrice) {
            stockService.sellStock(stockTradeDto, sellUser);
            returnMessageDto.setMsg("판매가 완료되었습니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.OK);
        } else {
            returnMessageDto.setMsg("판매금액을 다시 확인해주시기 바랍니다.");
            return new ResponseEntity<>(returnMessageDto, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @GetMapping("my-stock")
    public List<StockStatusDto> getMyStatus(HttpServletRequest request) {
        Integer myUser = (Integer) request.getAttribute("userNo");
        return stockService.getMyAccount(myUser);
    }

    @GetMapping("king")
    public List<StockRankingDto> getRankingData() {
        RankingDto rankingDto = rankingService.takeStockRanking();
        return stockService.getRankerData(rankingDto);
    }

    @GetMapping("news")
    public List<StockNewsDto> getAllNews() {
        List<StockNewsDto> allNews = stockService.getAllNews();
        return allNews;
    }

    @GetMapping("news/{stockNo}")
    public List<StockNewsDto> getAllNews(@PathVariable("stockNo") Integer stockNo) {
        List<StockNewsDto> detailNews = stockService.getDetailNews(stockNo);
        return detailNews;
    }

    @GetMapping("trade/{stockNo}")
    public List<StockTradeDetailDto> getTradeDetail(@PathVariable("stockNo") Integer stockNo) {
        List<StockTradeDetailDto> detailTrade = stockService.getTradeDetail(stockNo);
        return detailTrade;
    }
}
