package com.work;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {

    public void generateReport(Map<String, java.util.concurrent.atomic.AtomicInteger> data, String outputPath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            writer.write("===== USER ACTIVITY REPORT =====\n");

            Map<String, Integer> sorted = data.entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey(),
                            e -> e.getValue().get(),
                            (e1, e2) -> e1
                    ));

            sorted.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> {
                        try {
                            writer.write(entry.getKey() + ": " + entry.getValue() + " actions\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (IOException e) {
            System.out.println("Error writing report.");
            e.printStackTrace();
        }
    }
}