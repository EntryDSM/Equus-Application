package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.global.annotation.domain.Aggregate
import java.math.BigDecimal
import java.util.UUID

@Aggregate
data class QualificationCase(
    val id: UUID,
    val averageScore: BigDecimal,
    val receiptCode: UUID,
) : ApplicationCase(id, receiptCode)
