package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.global.annotation.Aggregate
import java.math.BigDecimal

@Aggregate
data class QualificationCase(
    val averageScore: BigDecimal,
    val receiptCode: Long,
) : ApplicationCase(receiptCode)
