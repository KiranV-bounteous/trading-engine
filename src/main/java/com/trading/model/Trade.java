package com.trading.model;

import java.time.LocalDateTime;

public class Trade {

    private final long tradeId;
    private final long accountId;
    private final String symbol;
    private final int quantity;
    private final double price;
    private final Side side;
    private final LocalDateTime timestamp;

    public Trade(long tradeId, long accountId, String symbol,
                 int quantity, double price,
                 Side side, LocalDateTime timestamp) {

        this.tradeId = tradeId;
        this.accountId = accountId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
        this.timestamp = timestamp;
    }

    public long getTradeId() { return tradeId; }
    public long getAccountId() { return accountId; }
    public String getSymbol() { return symbol; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Side getSide() { return side; }
    public LocalDateTime getTimestamp() { return timestamp; }
}