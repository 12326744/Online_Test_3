package com.work;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String directoryPath = System.getProperty("user.dir") + "/logs";
        String outputFile = System.getProperty("user.dir") + "/report.txt";

        File folder = new File(directoryPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid directory!");
            return;
        }

        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No log files found!");
            return;
        }

        ActivityAggregator aggregator = new ActivityAggregator();
        List<Thread> threads = new ArrayList<>();

        for (File file : files) {
            if (file.isFile()) {
                Thread t = new Thread(new LogProcessor(file.getAbsolutePath(), aggregator));
                threads.add(t);
                t.start();
            }
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ReportGenerator generator = new ReportGenerator();
        generator.generateReport(aggregator.getUserActivity(), outputFile);

        System.out.println("Report generated successfully!");
    }
}