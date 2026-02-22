package com.trading.util;

import com.trading.model.Side;
import com.trading.model.Trade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    public static List<Trade> load(String path) throws Exception {

        List<Trade> trades = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine(); // skip header

            String line;

            while ((line = br.readLine()) != null) {

                String[] t = line.split(",");

                trades.add(new Trade(
                        Long.parseLong(t[0]),
                        Long.parseLong(t[1]),
                        t[2],
                        Integer.parseInt(t[3]),
                        Double.parseDouble(t[4]),
                        Side.valueOf(t[5]),
                        LocalDateTime.parse(t[6])
                ));
            }
        }

        return trades;
    }
}