package com.work;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

public class ActivityAggregator {

    private ConcurrentHashMap<String, AtomicInteger> userActivity = new ConcurrentHashMap<>();

    public void incrementUser(String userId) {
        userActivity
                .computeIfAbsent(userId, k -> new AtomicInteger(0))
                .incrementAndGet();
    }

    public Map<String, AtomicInteger> getUserActivity() {
        return userActivity;
    }
}