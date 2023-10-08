package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.service.CheckTelService
import hs.kr.equus.application.domain.application.spi.ApplicationSecurityPort
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateInformationRequest
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class UpdateInformationUseCase(
    private val applicationSecurityPort: ApplicationSecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
    private val checkTelService: CheckTelService,
) {
    fun execute(request: UpdateInformationRequest) {
        val userId = applicationSecurityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)

        commandApplicationPort.save(
            application.copy(
                sex = request.sex,
                birthDate = request.birthDate,
                applicantName = request.applicantName,
                applicantTel = checkTelService.checkParentPutApplicantTel(userId, request.applicantTel),
                ParentName = request.parentName,
                ParentTel = request.parentTel
            )
        )
    }
}