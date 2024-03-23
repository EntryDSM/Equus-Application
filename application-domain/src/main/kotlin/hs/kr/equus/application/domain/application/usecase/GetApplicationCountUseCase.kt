package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationCountResponse
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class GetApplicationCountUseCase(
    private val queryApplicationPort: QueryApplicationPort,
) {
    fun execute(
        applicationType: ApplicationType,
        isDaejeon: Boolean,
    ): GetApplicationCountResponse {
        return queryApplicationPort.queryApplicationCountByApplicationTypeAndIsDaejeon(applicationType, isDaejeon)
    }
}
