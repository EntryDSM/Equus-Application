package hs.kr.equus.application.domain.applicationCase.domain.repository

import hs.kr.equus.application.domain.applicationCase.domain.entity.GraduationCaseJpaEntity
import hs.kr.equus.application.domain.applicationCase.domain.entity.QualificationCaseJpaEntity
import org.springframework.data.repository.CrudRepository

interface QualificationCaseJpaRepository : CrudRepository<QualificationCaseJpaEntity, Long> {
    fun existsByReceiptCode(receiptCode: Long): Boolean
    fun findByReceiptCode(receiptCode: Long): QualificationCaseJpaEntity?

    fun findAllByReceiptCodeIn(receiptCode: List<Long>): List<QualificationCaseJpaEntity?>
}