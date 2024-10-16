package hs.kr.equus.application.domain.school.domain.repository

import hs.kr.equus.application.domain.school.domain.entity.SchoolCacheRedisEntity
import org.springframework.data.repository.CrudRepository

interface SchoolCacheRepository : CrudRepository<SchoolCacheRedisEntity, String> {
}