package com.example.redisinspring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // Lettuce 라이브러리를 활용해서 Redis 연결을 관리하는 객체를 생성하고
        // Redis 서버에 대한 정보(host, port)를 설정함
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));

        // LettuceConnectionFactory : Redis에 대한 연결을 관리하는 Spring Data Redis의 기본 구현체입니다.
        // RedisStandaloneConfiguration 객체는 Redis 서버가 단독으로 실행되고 있음을 나타내며, 이를 생성할 때 호스트(host)와 포트(port) 값을 설정합니다.

    }
}
