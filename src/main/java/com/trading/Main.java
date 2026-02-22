package com.trading;

import com.trading.repository.TradeRepository;
import com.trading.service.PortfolioManager;
import com.trading.service.TradeProcessor;
import com.trading.util.CsvLoader;

public class Main {

    public static void main(String[] args) throws Exception {

        PortfolioManager portfolioManager = new PortfolioManager();
        TradeRepository repository = new TradeRepository();
        TradeProcessor processor =
                new TradeProcessor(8, portfolioManager, repository);

        CsvLoader.load("src/main/resources/trades.csv")
                .forEach(processor::submitTrade);

        processor.shutdown();

        Thread.sleep(3000);

        portfolioManager.getPortfolios()
                .forEach((account, positions) -> {
                    System.out.println("Account: " + account);
                    positions.forEach((symbol, position) ->
                            System.out.println(symbol + " -> " + position.getQuantity()));
                });
    }
}