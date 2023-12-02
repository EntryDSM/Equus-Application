package hs.kr.equus.application.domain.applicationCase.domain.repository

import hs.kr.equus.application.domain.applicationCase.domain.entity.GraduationCaseJpaEntity
import org.springframework.data.repository.CrudRepository

interface GraduationCaseJpaRepository : CrudRepository<GraduationCaseJpaEntity, Long> {
    fun existsByReceiptCode(receiptCode: Long): Boolean
    fun findByReceiptCode(receiptCode: Long): GraduationCaseJpaEntity?
}