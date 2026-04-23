package com.work;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogProcessor implements Runnable {

    private String filePath;
    private ActivityAggregator aggregator;

    public LogProcessor(String filePath, ActivityAggregator aggregator) {
        this.filePath = filePath;
        this.aggregator = aggregator;
    }

    @Override
    public void run() {
        System.out.println("Processing file: " + filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                processLine(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
    }

    private void processLine(String line) {
        String[] parts = line.trim().split("[,\\s]+");

        if (parts.length != 3) return;

        String userId = parts[1];

        aggregator.incrementUser(userId);
    }
}