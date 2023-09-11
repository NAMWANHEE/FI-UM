package com.example.pium.controller;

import com.example.pium.dto.*;
import com.example.pium.service.RankingServiceImp;
import com.example.pium.service.StockServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/stock")
@RestController
@Slf4j
public class StockController {
    private final StockServiceImp stockService;
    private final RankingServiceImp rankingService;

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

    @GetMapping("myAccount/{stockNo}")
    public StockAccountDto getDetailStockAccount(@PathVariable("stockNo") Integer stockNo) {
        Integer tmpUser = 1;
        StockAccountDto myAccountDetail = stockService.getDetailAccount(stockNo, tmpUser);
        return myAccountDetail;
    }

    @PostMapping("buying")
    public ResponseEntity<Map<String, String>> buyStock(@RequestBody StockTradeDto stockTradeDto) {
        Integer buyUser = 3;
        Boolean checkPrice = stockService.getStockNow(stockTradeDto.getStockNo(), stockTradeDto.getPrice());
        Map<String, String> returnMsg = new HashMap<>();
        if (checkPrice) {
            stockService.buyStock(stockTradeDto, buyUser);
            returnMsg.put("msg","구매가 완료되었습니다.");
            return new ResponseEntity<>(returnMsg, HttpStatus.OK);
        } else {
            returnMsg.put("msg","구매금액을 다시 확인해주시기 바랍니다.");
            return new ResponseEntity<>(returnMsg, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PostMapping("selling")
    public ResponseEntity<Map<String, String>> sellStock(@RequestBody StockTradeDto stockTradeDto) {
        Integer sellUser = 3;
        Boolean checkPrice = stockService.getStockNow(stockTradeDto.getStockNo(), stockTradeDto.getPrice());
        Map<String, String> returnMsg = new HashMap<>();
        if (checkPrice) {
            stockService.sellStock(stockTradeDto, sellUser);
            returnMsg.put("msg","판매가 완료되었습니다.");
            return new ResponseEntity<>(returnMsg, HttpStatus.OK);
        } else {
            returnMsg.put("msg","판매금액을 다시 확인해주시기 바랍니다.");
            return new ResponseEntity<>(returnMsg, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @GetMapping("myStock")
    public List<StockStatusDto> getMyStatus() {
        Integer myUser = 3;
        return stockService.getMyAccount(myUser);
    }

    @GetMapping("king")
    public List<StockRankingDto> getRankingData() {
        RankingDto rankingDto = rankingService.takeStockRanking();
        return stockService.getRankerData(rankingDto);
    }
}
