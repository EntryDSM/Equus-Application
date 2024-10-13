package hs.kr.equus.application.domain.user.domain.repository

import hs.kr.equus.application.domain.user.domain.entity.UserCacheRedisEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface UserCacheRepository : CrudRepository<UserCacheRedisEntity, UUID> {
}