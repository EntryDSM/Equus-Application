package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationCountResponse
import java.util.UUID

interface QueryApplicationPort {
    fun queryApplicationByUserId(userId: UUID): Application?

    fun isExistsApplicationByUserId(userId: UUID): Boolean

    fun queryApplicationCountByApplicationTypeAndIsDaejeon(
        applicationType: ApplicationType,
        isDaejeon: Boolean,
    ): GetApplicationCountResponse
}
