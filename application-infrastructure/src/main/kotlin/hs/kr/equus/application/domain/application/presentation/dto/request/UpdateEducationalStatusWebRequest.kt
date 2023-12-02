package hs.kr.equus.application.domain.application.presentation.dto.request

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import java.time.LocalDate
import javax.validation.constraints.NotNull

data class UpdateEducationalStatusWebRequest(
    @NotNull
    val graduateDate: LocalDate,
    @NotNull
    val educationalStatus: EducationalStatus,
)
