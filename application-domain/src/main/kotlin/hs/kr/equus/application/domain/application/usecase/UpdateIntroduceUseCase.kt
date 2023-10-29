package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateIntroduceRequest
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class UpdateIntroduceUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
) {
    fun execute(request: UpdateIntroduceRequest) {
        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)

        commandApplicationPort.save(
            application.copy(
                selfIntroduce = request.content,
            ),
        )
    }
}
