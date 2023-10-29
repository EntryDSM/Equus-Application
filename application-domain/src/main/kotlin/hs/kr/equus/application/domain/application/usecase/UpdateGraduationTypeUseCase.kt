package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateGraduationTypeRequest
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.model.Qualification
import hs.kr.equus.application.domain.graduationInfo.service.CheckGraduateDateService
import hs.kr.equus.application.domain.graduationInfo.spi.*
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort
import java.time.LocalDate

@UseCase
class UpdateGraduationTypeUseCase(
    private val checkGraduateDateService: CheckGraduateDateService,
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
    private val applicationQueryGraduationPort: ApplicationQueryGraduationPort,
    private val applicationCommandGraduationPort: ApplicationCommandGraduationPort,
    private val applicationQueryQualificationPort: ApplicationQueryQualificationPort,
    private val applicationCommandQualificationPort: ApplicationCommandQualificationPort,
) {
    fun execute(request: UpdateGraduationTypeRequest) {
        checkGraduateDateService.checkIsInvalidYear(
            request.educationalStatus,
            request.graduateDate,
        )

        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        commandApplicationPort.save(
            application.copy(
                educationalStatus = request.educationalStatus,
            ),
        )

        updateGraduationInfo(
            application.receiptCode!!,
            request.graduateDate,
            request.educationalStatus,
        )
    }

    private fun updateGraduationInfo( // TODO 팩토리 패턴으로 옮기기
        receiptCode: Long,
        graduateDate: LocalDate,
        educationalStatus: EducationalStatus,
    ) {
        if (educationalStatus == EducationalStatus.QUALIFICATION_EXAM) {
            applicationCommandQualificationPort.save(
                Qualification(
                    receiptCode = receiptCode,
                    qualifiedDate = graduateDate,
                    educationalStatus = educationalStatus,
                ),
            )
            applicationQueryGraduationPort.queryGraduationByReceiptCode(receiptCode)?.let {
                applicationCommandGraduationPort.delete(it)
            }
        } else {
            applicationCommandGraduationPort.save(
                Graduation(
                    receiptCode = receiptCode,
                    graduateDate = graduateDate,
                    educationalStatus = educationalStatus,
                ),
            )
            applicationQueryQualificationPort.queryQualificationByReceiptCode(receiptCode)?.let {
                applicationCommandQualificationPort.delete(it)
            }
        }
    }
}
