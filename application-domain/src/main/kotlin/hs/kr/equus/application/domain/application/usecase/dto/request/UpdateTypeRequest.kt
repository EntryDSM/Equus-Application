package hs.kr.equus.application.domain.application.usecase.dto.request

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType

data class UpdateTypeRequest(
    val applicationType: ApplicationType,
    val applicationRemark: ApplicationRemark?,
    val isDaejeon: Boolean,
    val isOutOfHeadcount: Boolean,
)
