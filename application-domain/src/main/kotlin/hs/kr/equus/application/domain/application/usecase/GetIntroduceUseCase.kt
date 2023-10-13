package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.ApplicationSecurityPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetIntroduceResponse
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class GetIntroduceUseCase(
    private val applicationSecurityPort: ApplicationSecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
) {
    fun execute(): GetIntroduceResponse {
        val userId = applicationSecurityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)

        return GetIntroduceResponse(application.selfIntroduce)
    }
}
