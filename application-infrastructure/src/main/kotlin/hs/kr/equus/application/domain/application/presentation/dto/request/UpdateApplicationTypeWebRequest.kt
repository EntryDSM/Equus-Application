package hs.kr.equus.application.domain.application.presentation.dto.request

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import javax.validation.constraints.NotNull

data class UpdateApplicationTypeWebRequest(
    @NotNull
    val applicationType: ApplicationType,
    val applicationRemark: ApplicationRemark?,
    @NotNull
    val isDaejeon: Boolean,
    @NotNull
    val isOutOfHeadcount: Boolean,
    val veteransNumber: Int?
)
