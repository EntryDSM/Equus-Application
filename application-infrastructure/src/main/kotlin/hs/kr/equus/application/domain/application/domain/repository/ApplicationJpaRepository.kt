package hs.kr.equus.application.domain.application.domain.repository

import hs.kr.equus.application.domain.application.domain.entity.ApplicationJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ApplicationJpaRepository : CrudRepository<ApplicationJpaEntity, Long> {
    fun findByUserId(userId: UUID): ApplicationJpaEntity?
    fun existsByUserId(userId: UUID): Boolean
    fun findReceiptCodeByUserId(userId: UUID): Long?
}
