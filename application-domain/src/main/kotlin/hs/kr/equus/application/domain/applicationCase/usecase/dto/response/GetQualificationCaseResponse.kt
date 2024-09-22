package hs.kr.equus.application.domain.applicationCase.usecase.dto.response

import java.math.BigDecimal

data class GetQualificationCaseResponse(
    val koreanGrade: BigDecimal = BigDecimal.ZERO,
    val socialGrade:  BigDecimal = BigDecimal.ZERO,
    val mathGrade:  BigDecimal = BigDecimal.ZERO,
    val scienceGrade:  BigDecimal = BigDecimal.ZERO,
    val englishGrade:  BigDecimal = BigDecimal.ZERO,
    val optGrade:  BigDecimal = BigDecimal.ZERO,
    val extraScore: GetExtraScoreResponse
)