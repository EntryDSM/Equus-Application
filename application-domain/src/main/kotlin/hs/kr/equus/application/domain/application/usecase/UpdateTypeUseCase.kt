package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.ApplicationSecurityPort
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateTypeRequest
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class UpdateTypeUseCase(
    private val applicationSecurityPort: ApplicationSecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
) {
    fun execute(request: UpdateTypeRequest) {
        val userId = applicationSecurityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)

        request.run {
            commandApplicationPort.save(
                application.copy(
                    applicationType = applicationType,
                    applicationRemark = applicationRemark,
                    isDaejeon = isDaejeon,
                    isOutOfHeadcount = isOutOfHeadcount,
                ),
            )
        }
    }
}
