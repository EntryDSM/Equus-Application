package hs.kr.equus.application.domain.applicationCase.presentation.dto.request

import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.ExtraScoreRequest
import java.math.BigDecimal
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull

data class UpdateQualificationCaseWebRequest(
    val koreanGrade: BigDecimal,
    val socialGrade: BigDecimal,
    val mathGrade: BigDecimal,
    val scienceGrade: BigDecimal,
    val englishGrade: BigDecimal,
    val optGrade: BigDecimal,
    val extraScore: ExtraScoreRequest
)
