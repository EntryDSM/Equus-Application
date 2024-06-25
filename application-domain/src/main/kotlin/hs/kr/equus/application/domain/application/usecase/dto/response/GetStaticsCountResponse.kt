package hs.kr.equus.application.domain.application.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.ApplicationType


data class GetStaticsCountResponse(
     val applicationType: ApplicationType,

     val isDaejeon: Boolean,

     val count: Int
)