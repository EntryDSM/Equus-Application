package hs.kr.equus.application.domain.applicationCase.domain.entity

import hs.kr.equus.application.domain.applicationCase.domain.entity.vo.ExtraScoreItem
import java.math.BigDecimal
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_qualification_case")
class QualificationCaseJpaEntity(
    override val id: Long,
    val averageScore: BigDecimal,
    override val receiptCode: Long,
    @Embedded
    override val extraScoreItem: ExtraScoreItem,
) : ApplicationCaseEntity(
    id = id,
    receiptCode = receiptCode,
    extraScoreItem = extraScoreItem
)