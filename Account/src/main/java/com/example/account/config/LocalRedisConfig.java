package com.example.account.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class LocalRedisConfig {  //----redis를 띄우고 제거하는 역할만 하는 파일-----
    @Value("${spring.redis.port}")  //application.yml 파일에서 spring아래계층
    private int redisPort;  //redis 아래의 port를 가져와서 redisPort를 담는다는뜻인듯


    private RedisServer redisServer;


    @PostConstruct
    public void startRedis() {
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

}
