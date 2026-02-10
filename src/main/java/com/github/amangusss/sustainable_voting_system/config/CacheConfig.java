package com.github.amangusss.sustainable_voting_system.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.data.redis.RedisConnectionFailureException;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger log = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public RedisCacheManager cacheManager(
            RedisConnectionFactory connectionFactory,
            @Value("${app.cache.images-ttl:10m}") Duration imagesTtl) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(imagesTtl)
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

    @Bean
    public CacheErrorHandler cacheErrorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                handleRedisError("GET", exception, cache, key);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                handleRedisError("PUT", exception, cache, key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                handleRedisError("EVICT", exception, cache, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                handleRedisError("CLEAR", exception, cache, null);
            }

            private void handleRedisError(String action, RuntimeException exception, Cache cache, Object key) {
                if (isRedisUnavailable(exception)) {
                    log.warn("Cache {} skipped due to Redis error cache={} key={} cause={}",
                            action,
                            cache != null ? cache.getName() : "unknown",
                            key,
                            exception.getMessage());
                    return;
                }
                throw exception;
            }

            private boolean isRedisUnavailable(RuntimeException exception) {
                if (exception instanceof RedisConnectionFailureException) {
                    return true;
                }
                Throwable cause = exception.getCause();
                return cause instanceof RedisConnectionFailureException;
            }
        };
    }
}
