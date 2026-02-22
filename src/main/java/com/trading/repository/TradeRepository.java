package com.trading.repository;

import com.trading.model.Trade;

import java.sql.*;

public class TradeRepository {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/tradingdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public void save(Trade trade) {

        String sql = """
                INSERT INTO trades 
                (trade_id, account_id, symbol, quantity, price, side, timestamp)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, trade.getTradeId());
            ps.setLong(2, trade.getAccountId());
            ps.setString(3, trade.getSymbol());
            ps.setInt(4, trade.getQuantity());
            ps.setDouble(5, trade.getPrice());
            ps.setString(6, trade.getSide().name());
            ps.setTimestamp(7, Timestamp.valueOf(trade.getTimestamp()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}