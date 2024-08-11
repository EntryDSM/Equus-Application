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
    override val receiptCode: Long,
    @Embedded
    override val extraScoreItem: ExtraScoreItem,
    val koreanGrade: BigDecimal,
    val socialGrade: BigDecimal,
    val optGrade: BigDecimal,
    val mathGrade: BigDecimal,
    val scienceGrade: BigDecimal,
    val englishGrade: BigDecimal,
) : ApplicationCaseEntity(
    id = id,
    receiptCode = receiptCode,
    extraScoreItem = extraScoreItem
)