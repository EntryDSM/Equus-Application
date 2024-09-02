package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateApplicationTypeRequest
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class UpdateApplicationTypeUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
) {
    fun execute(request: UpdateApplicationTypeRequest) {
        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        request.run {
            commandApplicationPort.save(
                application.copy(
                    applicationType = applicationType,
                    applicationRemark = applicationRemark,
                    isDaejeon = isDaejeon,
                    isOutOfHeadcount = isOutOfHeadcount,
                    veteransNumber = veteransNumber
                ),
            )
        }
    }
}
