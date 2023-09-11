package com.example.pium.service;

import com.example.pium.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockServiceImp {
    private final StockEventRepository stockEventRepository;
    private final StockAccountRepository stockAccountRepository;
    private final StockDataRepository stockDataRepository;
    private final StockNewsRepository stockNewsRepository;
    private final StockTradeRepository stockTradeRepository;
}
