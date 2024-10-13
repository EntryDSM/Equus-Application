package hs.kr.equus.application.domain.status.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(value = "status_cache")
class StatusCacheRedisEntity(
    @Id
    val receiptCode: Long,

    val isPrintsArrived: Boolean,

    val isSubmitted: Boolean,

    val examCode: String?,

    val isFirstRoundPass: Boolean,

    val isSecondRoundPass: Boolean,

    @TimeToLive
    var ttl: Long
)