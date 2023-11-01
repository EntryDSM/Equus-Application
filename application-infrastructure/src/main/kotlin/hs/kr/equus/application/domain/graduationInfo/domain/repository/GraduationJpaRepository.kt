package hs.kr.equus.application.domain.graduationInfo.domain.repository

import hs.kr.equus.application.domain.graduationInfo.domain.entity.GraduationJpaEntity
import org.springframework.data.repository.CrudRepository

interface GraduationJpaRepository : CrudRepository<GraduationJpaEntity, Long> {
    fun findByReceiptCode(receiptCode: Long): GraduationJpaEntity?
}
