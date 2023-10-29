package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateStudyPlanRequest
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class UpdateStudyPlanUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
) {
    fun execute(request: UpdateStudyPlanRequest) {
        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)

        commandApplicationPort.save(
            application.copy(
                studyPlan = request.content,
            ),
        )
    }
}
