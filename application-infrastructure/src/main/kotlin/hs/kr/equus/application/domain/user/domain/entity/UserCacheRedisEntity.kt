package hs.kr.equus.application.domain.user.domain.entity

import hs.kr.equus.application.global.security.jwt.UserRole
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*

@RedisHash(value = "status_cache")
class UserCacheRedisEntity (
    @Id
    val id: UUID,
    val phoneNumber: String,
    val name: String,
    val isParent: Boolean,
    val receiptCode: Long?,
    val role: UserRole,
    @TimeToLive
    val ttl: Long
)