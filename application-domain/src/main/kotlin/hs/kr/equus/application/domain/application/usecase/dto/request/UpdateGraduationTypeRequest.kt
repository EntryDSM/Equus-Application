package hs.kr.equus.application.domain.application.usecase.dto.request

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import java.time.LocalDate

data class UpdateGraduationTypeRequest(
    val graduateDate: LocalDate,
    val educationalStatus: EducationalStatus,
)