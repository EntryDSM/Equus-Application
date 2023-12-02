package hs.kr.equus.application.domain.application.usecase.dto.request

import hs.kr.equus.application.domain.application.model.types.EducationalStatus

data class UpdateEducationalStatusRequest(
    val educationalStatus: EducationalStatus,
)
