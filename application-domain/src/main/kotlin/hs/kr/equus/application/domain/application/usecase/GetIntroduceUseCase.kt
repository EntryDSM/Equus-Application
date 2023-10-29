package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetIntroduceResponse
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class GetIntroduceUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
) {
    fun execute(): GetIntroduceResponse {
        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)

        return GetIntroduceResponse(application.selfIntroduce)
    }
}
