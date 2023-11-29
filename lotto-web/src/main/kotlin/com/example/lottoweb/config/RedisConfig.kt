package com.example.lottoweb.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

/**
 * @author Unagi_zoso
 * @since 2023-11-22
 *
 * Redis 설정
 * RedisTemplate, RedisCacheManager 설정
 * @see com.example.lottoweb.aspect.CoolDownAspect
 */
@Configuration
@EnableCaching
class RedisConfig {
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory
        return redisTemplate
    }

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory?): RedisCacheManager {
        val cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMillis(COOL_DOWN_TIME)) // 캐시 만료 시간 설정
            .disableCachingNullValues()
        return RedisCacheManager.builder(connectionFactory!!)
            .cacheDefaults(cacheConfiguration)
            .build()
    }

    companion object {
        const val COOL_DOWN_TIME = 300L
    }
}
