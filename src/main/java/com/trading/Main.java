package com.trading;

import com.trading.model.Position;
import com.trading.repository.TradeRepository;
import com.trading.service.PortfolioManager;
import com.trading.service.TradeProcessor;
import com.trading.util.CsvLoader;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();

        PortfolioManager portfolioManager = new PortfolioManager();
        TradeRepository repository = new TradeRepository();
        TradeProcessor processor =
                new TradeProcessor(8, portfolioManager, repository);

        // Load trades and submit asynchronously
        CsvLoader.load("src/main/resources/trades.csv")
                .forEach(processor::submitTrade);

        // Proper shutdown
        processor.shutdown();
        processor.awaitTermination();

        long endTime = System.currentTimeMillis();

        // ===============================
        //        ACCOUNT REPORT
        // ===============================
        System.out.println("\n====================================================");
        System.out.println("                ACCOUNT SUMMARY REPORT");
        System.out.println("====================================================\n");

        portfolioManager.getPortfolios()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey()) // sort accounts
                .forEach(accountEntry -> {

                    Long accountId = accountEntry.getKey();
                    Map<String, Position> positions = accountEntry.getValue();

                    int totalQuantity = positions.values()
                            .stream()
                            .mapToInt(Position::getQuantity)
                            .sum();

                    double totalValue = positions.values()
                            .stream()
                            .mapToDouble(pos -> pos.getQuantity() * pos.getAveragePrice())
                            .sum();

                    System.out.println("Account ID        : " + accountId);
                    System.out.println("Total Positions   : " + positions.size());
                    System.out.println("Total Quantity    : " + totalQuantity);
                    System.out.printf("Total Value       : $%.2f%n", totalValue);

                    System.out.println("\nHoldings:");
                    System.out.println("--------------------------------------------------");
                    System.out.printf("%-10s %-10s %-15s %-15s%n",
                            "Symbol", "Quantity", "Avg Price", "Total Value");
                    System.out.println("--------------------------------------------------");

                    positions.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey())
                            .forEach(posEntry -> {
                                String symbol = posEntry.getKey();
                                Position pos = posEntry.getValue();
                                int qty = pos.getQuantity();
                                double avgPrice = pos.getAveragePrice();
                                double value = qty * avgPrice;

                                System.out.printf("%-10s %-10d $%-14.2f $%-14.2f%n",
                                        symbol, qty, avgPrice, value);
                            });

                    System.out.println("--------------------------------------------------\n");
                });

        System.out.println("====================================================");
        System.out.println("Total Execution Time: " + (endTime - startTime) + " ms");
        System.out.println("====================================================");
    }
}