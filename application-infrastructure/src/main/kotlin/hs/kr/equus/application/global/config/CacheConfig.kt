package hs.kr.equus.application.global.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun redisCacheConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(60)) // TTL 60초 후 제거됨
            .disableCachingNullValues() // NULL 은 저장 안 됨
            .serializeKeysWith( // cache key 문자열로 직렬화
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith( // cache value 를 json 으로 직렬화
                RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer())
            )
    }
}