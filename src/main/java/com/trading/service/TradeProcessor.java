package com.trading.service;

import com.trading.model.Trade;
import com.trading.repository.TradeRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TradeProcessor {

    private final ExecutorService executor;
    private final PortfolioManager portfolioManager;
    private final TradeRepository repository;

    public TradeProcessor(int threadPoolSize,
                          PortfolioManager portfolioManager,
                          TradeRepository repository) {

        this.executor = Executors.newFixedThreadPool(threadPoolSize);
        this.portfolioManager = portfolioManager;
        this.repository = repository;
    }

    public void submitTrade(Trade trade) {

        executor.submit(() -> {

            if (trade.getQuantity() <= 0) {
                throw new IllegalArgumentException(
                        "Invalid trade quantity"
                );
            }

            portfolioManager.processTrade(trade);
            repository.save(trade);
        });
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void awaitTermination() throws InterruptedException {
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}