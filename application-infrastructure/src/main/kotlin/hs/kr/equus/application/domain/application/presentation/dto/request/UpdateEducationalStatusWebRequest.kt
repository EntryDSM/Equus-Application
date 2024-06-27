package hs.kr.equus.application.domain.application.presentation.dto.request

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import java.time.YearMonth
import javax.validation.constraints.NotNull

data class UpdateEducationalStatusWebRequest(
    @NotNull
    val graduateDate: YearMonth,
    @NotNull
    val educationalStatus: EducationalStatus,
)
