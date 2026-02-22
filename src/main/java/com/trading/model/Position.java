package com.trading.model;

public class Position {

    private int quantity;

    public synchronized void applyTrade(Side side, int qty) {

        if (side == Side.BUY) {
            quantity += qty;
        } else {
            if (quantity - qty < 0) {
                throw new IllegalArgumentException(
                        "Negative position not allowed"
                );
            }
            quantity -= qty;
        }
    }

    public synchronized int getQuantity() {
        return quantity;
    }
}