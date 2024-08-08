package hs.kr.equus.application.domain.applicationCase.usecase.dto.request

import java.math.BigDecimal

data class UpdateQualificationCaseRequest(
    val koreanGrade: BigDecimal,
    val socialGrade: BigDecimal,
    val mathGrade: BigDecimal,
    val scienceGrade: BigDecimal,
    val englishGrade: BigDecimal,
    val optGrade: BigDecimal,
    val extraScore: ExtraScoreRequest
)
