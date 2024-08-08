package hs.kr.equus.application.domain.applicationCase.presentation.dto.request

import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.ExtraScoreRequest
import java.math.BigDecimal
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull

data class UpdateQualificationCaseWebRequest(
    val koreanGrade: BigDecimal = BigDecimal.ZERO,
    val socialGrade: BigDecimal = BigDecimal.ZERO,
    val mathGrade: BigDecimal = BigDecimal.ZERO,
    val scienceGrade: BigDecimal = BigDecimal.ZERO,
    val englishGrade: BigDecimal = BigDecimal.ZERO,
    val optGrade: BigDecimal = BigDecimal.ZERO,
    val extraScore: ExtraScoreRequest
)
