package hs.kr.equus.application.domain.score.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import java.math.BigDecimal

data class GetStaticsScoreResponse(
    val isDaejeon: Boolean,
    val applicationType: ApplicationType,
    var indices: List<Int>
)
