package hs.kr.equus.application.domain.graduationInfo.usecase

import hs.kr.equus.application.domain.graduationInfo.service.CheckGraduateDateService
import hs.kr.equus.application.domain.graduationInfo.spi.CommandGraduationInfoPort
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoSecurityPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.domain.graduationInfo.usecase.dto.request.UpdateGraduationTypeRequest
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class UpdateGraduationTypeUseCase(
    private val graduationInfoSecurityPort: GraduationInfoSecurityPort,
    private val graduationInfoQueryApplicationPort: GraduationInfoQueryApplicationPort,
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val commandGraduationInfoPort: CommandGraduationInfoPort,
    private val checkGraduateDateService: CheckGraduateDateService,
) {
    fun execute(request: UpdateGraduationTypeRequest) {
        checkGraduateDateService.checkIsInvalidYear(
            request.educationalStatus,
            request.graduateDate
        )

        val userId = graduationInfoSecurityPort.getCurrentUserId()
        val receiptCode = graduationInfoQueryApplicationPort.queryReceiptCodeByUserId(userId)

        val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByReceiptCode(receiptCode)
        commandGraduationInfoPort.save(
            graduationInfo.copy(
                educationalStatus = request.educationalStatus,
                graduateDate = request.graduateDate
            )
        )
    }
}
