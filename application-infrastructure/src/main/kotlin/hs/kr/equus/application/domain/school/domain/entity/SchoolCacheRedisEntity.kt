package hs.kr.equus.application.domain.school.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("school_cache")
data class SchoolCacheRedisEntity(
    @Id
    val code: String,
    val name: String,
    val tel: String,
    val type: String,
    val address: String,
    val regionName: String
)
