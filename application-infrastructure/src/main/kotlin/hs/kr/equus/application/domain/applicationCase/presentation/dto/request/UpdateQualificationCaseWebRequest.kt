package hs.kr.equus.application.domain.applicationCase.presentation.dto.request

import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.ExtraScoreRequest
import java.math.BigDecimal
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull

data class UpdateQualificationCaseWebRequest(
    @field:DecimalMin("0.0") @field:DecimalMax("100.0")  @field:NotNull
    val koreanGrade: BigDecimal = BigDecimal.ZERO,
    @field:DecimalMin("0.0") @field:DecimalMax("100.0")  @field:NotNull
    val socialGrade: BigDecimal = BigDecimal.ZERO,
    @field:DecimalMin("0.0") @field:DecimalMax("100.0")  @field:NotNull
    val mathGrade: BigDecimal = BigDecimal.ZERO,
    @field:DecimalMin("0.0") @field:DecimalMax("100.0")  @field:NotNull
    val scienceGrade: BigDecimal = BigDecimal.ZERO,
    @field:DecimalMin("0.0") @field:DecimalMax("100.0")  @field:NotNull
    val englishGrade: BigDecimal = BigDecimal.ZERO,
    @field:DecimalMin("0.0") @field:DecimalMax("100.0")  @field:NotNull
    val optGrade: BigDecimal = BigDecimal.ZERO,
    @field:NotNull
    val extraScore: ExtraScoreRequest
)