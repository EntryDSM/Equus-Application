package hs.kr.equus.application.domain.application.usecase.dto.request

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import java.time.LocalDate

data class UpdateEducationalStatusRequest(
    val educationalStatus: EducationalStatus,
    val graduateDate: LocalDate
)
