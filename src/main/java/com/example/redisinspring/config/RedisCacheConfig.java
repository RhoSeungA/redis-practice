package com.example.redisinspring.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching // Spring Boot의 캐싱 설정을 활성화
public class RedisCacheConfig {
    // CacheManager -> org.springframework.cache.CacheManager;
    // 캐시를 관리하는 인터페이스로, 여기서는 Redis를 캐시로 사용하는 RedisCacheManager를 반환

    // RedisCacheConfiguration : org.springframework.data.redis.cache.RedisCacheConfiguration
    // Redis 캐시의 기본 설정을 담당
    // defaultCacheConfig() 메서드는 기본 캐시 설정을 생성하는데, 이를 기반으로 키와 값을 어떻게 직렬화할지, 만료 기간 등을 추가로 설정할 수 있습니다.



    @Bean
    public CacheManager boardCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                // Redis에 Key를 저장할 때 String으로 직렬화(변환)해서 저장
                // Redis에 저장할 Key 값을 StringRedisSerializer로 직렬화합니다. 즉, Key를 문자열로 변환하여 Redis에 저장합니다.
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new StringRedisSerializer()))
                // Redis에 Value를 저장할 때 Json으로 직렬화(변환)해서 저장
                // Jackson2JsonRedisSerializer는 Java 객체를 JSON으로 변환하거나, JSON 데이터를 Java 객체로 역직렬화하는 데 사용됩니다. 여기서 Object.class를 전달하여 모든 객체를 JSON으로 변환할 수 있도록 설정합니다.
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new Jackson2JsonRedisSerializer<Object>(Object.class)
                        )
                )
                // 데이터의 만료기간(TTL) 설정 - 1분
                // 캐시 데이터의 만료 기간(TTL, Time To Live)을 1분으로 설정
                .entryTtl(Duration.ofMinutes(1L));

        // RedisCacheManagerBuilder를 사용해 RedisConnectionFactory를 기반으로 캐시 매니저를 생성합니다.
        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory) // RedisConnectionFactory는 Redis 서버와의 연결을 설정하는 객체
                .cacheDefaults(redisCacheConfiguration) // // 앞서 정의한 redisCacheConfiguration을 기본 설정으로 적용
                .build();
    }
}
