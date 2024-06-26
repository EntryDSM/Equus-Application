package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.usecase.dto.response.GetStaticsCountResponse

interface QueryStaticsCountPort {
    fun queryStaticsCount(
        applicationType: ApplicationType,
        isDaejeon: Boolean
    ): GetStaticsCountResponse
}