package com.example.account.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisRepositoryConfig {
    //-----

    @Value("${spring.redis.host}")//yml에 넣어줫던 port에서
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public RedissonClient redissonClient() {  //config : 설정이라는뜻이라고함
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
        return Redisson.create(config);
    }       //yml에 써놓은 설정들 기반으로해서 Redisson하나를 생성하고 반환하는 함수인듯

}
