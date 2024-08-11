package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.types.ParentRelation
import hs.kr.equus.application.domain.application.service.CheckTelService
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateInformationRequest
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class UpdateInformationUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
    private val checkTelService: CheckTelService,
) {
    fun execute(request: UpdateInformationRequest) {
        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        request.run {
            commandApplicationPort.save(
                application.copy(
                    sex = sex,
                    birthDate = birthDate,
                    applicantName = applicantName,
                    applicantTel = checkTelService.checkParentPutApplicantTel(userId, applicantTel),
                    parentName = parentName,
                    parentTel = parentTel,
                    parentRelation = parentRelation,
                    streetAddress = streetAddress,
                    postalCode = postalCode,
                    detailAddress = detailAddress,
                ),
            )
        }
    }
}
