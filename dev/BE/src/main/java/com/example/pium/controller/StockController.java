package com.example.pium.controller;

import com.example.pium.service.StockServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/stock")
@RestController
@Slf4j
public class StockController {
    private final StockServiceImp stockService;

}
