package com.trading.model;

public class Position {

    private int quantity;
    private double averagePrice;  // average price per unit

    public Position() {
        this.quantity = 0;
        this.averagePrice = 0.0;
    }

    /**
     * Apply a trade to this position.
     * Updates quantity and average price.
     * @param side BUY or SELL
     * @param qty quantity of shares
     * @param price price per share
     */
    public synchronized void applyTrade(Side side, int qty, double price) {

        if (side == Side.BUY) {
            // New weighted average price calculation
            double totalCostBefore = this.averagePrice * this.quantity;
            double tradeCost = price * qty;
            this.quantity += qty;
            this.averagePrice = (totalCostBefore + tradeCost) / this.quantity;

        } else {
            // SELL: reduce quantity, price stays same
            if (quantity - qty < 0) {
                throw new IllegalArgumentException(
                        "Negative position not allowed"
                );
            }
            this.quantity -= qty;

            // Optionally: If quantity becomes 0, reset price
            if (this.quantity == 0) {
                this.averagePrice = 0.0;
            }
        }
    }

    public synchronized int getQuantity() {
        return quantity;
    }

    public synchronized double getAveragePrice() {
        return averagePrice;
    }
}