package com.trading.service;

import com.trading.model.Position;
import com.trading.model.Trade;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PortfolioManager {

    private final Map<Long, Map<String, Position>> portfolios =
            new ConcurrentHashMap<>();

    public void processTrade(Trade trade) {

        portfolios
                .computeIfAbsent(trade.getAccountId(),
                        acc -> new ConcurrentHashMap<>())
                .computeIfAbsent(trade.getSymbol(),
                        sym -> new Position())
                .applyTrade(trade.getSide(), trade.getQuantity(), trade.getPrice());
    }

    public Map<Long, Map<String, Position>> getPortfolios() {
        return portfolios;
    }
}