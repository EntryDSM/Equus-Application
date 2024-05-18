package hs.kr.equus.application.domain.application.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus

data class GetApplicationTypeResponse(
    val educationalStatus: EducationalStatus?,
    val applicationType: ApplicationType?,
    val isDaejeon: Boolean?,
    val applicationRemark: ApplicationRemark?,
    val isOutOfHeadCount: Boolean?
)
