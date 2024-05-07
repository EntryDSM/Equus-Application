package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.ApplicationQueryUserPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationTypeResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@ReadOnlyUseCase
class GetApplicationTypeUseCase(
    private val queryApplicationPort: QueryApplicationPort,
    private val applicationQueryUserPort: ApplicationQueryUserPort,
    private val securityPort: SecurityPort
) {
    fun execute(): GetApplicationTypeResponse {
        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        return GetApplicationTypeResponse(
            educationalStatus = application.educationalStatus,
            applicationType = application.applicationType,
            isDaejeon = application.isDaejeon,
            applicationRemark = application.applicationRemark,
            isOutOfHeadCount = application.isOutOfHeadcount
        )
    }
}