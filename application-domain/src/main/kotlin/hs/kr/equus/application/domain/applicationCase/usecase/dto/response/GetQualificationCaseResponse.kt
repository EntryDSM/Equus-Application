package hs.kr.equus.application.domain.applicationCase.usecase.dto.response

import java.math.BigDecimal

data class GetQualificationCaseResponse(
    val averageScore: BigDecimal,
    val extraScore: GetExtraScoreResponse
)