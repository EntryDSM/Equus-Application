package hs.kr.equus.application.domain.applicationCase.presentation.dto.request

import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.ExtraScoreRequest
import java.math.BigDecimal
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull

data class UpdateQualificationCaseWebRequest(
    @DecimalMin("60.0")
    @DecimalMax("100.0")
    @Digits(integer = 3, fraction = 2)
    @NotNull(message = "ged_average_score는 null이면 안됩니다.")
    val averageScore: BigDecimal,
    val extraScore: ExtraScoreRequest
)
