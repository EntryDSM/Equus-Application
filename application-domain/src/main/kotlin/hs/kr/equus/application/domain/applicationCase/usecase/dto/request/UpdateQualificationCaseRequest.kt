package hs.kr.equus.application.domain.applicationCase.usecase.dto.request

import java.math.BigDecimal

data class UpdateQualificationCaseRequest(
    val averageScore: BigDecimal = BigDecimal(0),
    val extraScore: ExtraScoreRequest
)
