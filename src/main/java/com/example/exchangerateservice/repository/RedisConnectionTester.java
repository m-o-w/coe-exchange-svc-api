package com.example.exchangerateservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionTester implements CommandLineRunner {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void run(String... args) throws Exception {
        try {
            redisConnectionFactory.getConnection().ping();
            System.out.println("INT101_FOREX_API: Successfully connected to Redis!");
        } catch (Exception e) {
            System.err.println("INT101_FOREX_API: Failed to connect to Redis: " + e.getMessage());
        }
    }
}
