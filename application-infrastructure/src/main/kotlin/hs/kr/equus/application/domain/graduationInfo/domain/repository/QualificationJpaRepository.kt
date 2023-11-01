package hs.kr.equus.application.domain.graduationInfo.domain.repository

import hs.kr.equus.application.domain.graduationInfo.domain.entity.QualificationJpaEntity
import org.springframework.data.repository.CrudRepository

interface QualificationJpaRepository : CrudRepository<QualificationJpaEntity, Long> {
    fun findByReceiptCode(receiptCode: Long): QualificationJpaEntity?
}
