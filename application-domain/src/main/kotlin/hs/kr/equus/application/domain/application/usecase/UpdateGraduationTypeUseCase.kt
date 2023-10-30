package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateGraduationTypeRequest
import hs.kr.equus.application.domain.graduationInfo.factory.GraduationInfoFactory
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
    private val applicationCommandGraduationInfoPort: ApplicationCommandGraduationInfoPort,
    private val applicationQueryGraduationInfoPort: ApplicationQueryGraduationInfoPort,
    private val graduationInfoFactory: GraduationInfoFactory,
) {
    fun execute(request: UpdateGraduationTypeRequest) {
        checkGraduateDateService.checkIsInvalidYear(
            request.educationalStatus,
            request.graduateDate,
        )

        val userId = securityPort.getCurrentUserId()
        val application =
            queryApplicationPort.queryApplicationByUserId(userId)
                ?: throw ApplicationExceptions.ApplicationNotFoundException()

        application.run {
            if (educationalStatus != request.educationalStatus) {
                educationalStatus?.let {
                    deleteOtherCase(
                        receiptCode!!,
                        educationalStatus,
                    )
                }
            }
        }

        commandApplicationPort.save(
            application.copy(
                educationalStatus = request.educationalStatus,
            ),
        )

        applicationCommandGraduationInfoPort.save(
            graduationInfoFactory.createGraduationInfo(
                receiptCode = application.receiptCode!!,
                educationalStatus = request.educationalStatus,
                graduationDate = request.graduateDate,
            )
        )
    }

    private fun deleteOtherCase(
        receiptCode: Long,
        educationalStatus: EducationalStatus,
    ) {
        applicationQueryGraduationInfoPort.queryByReceiptCodeAndEducationalStatus(
            receiptCode,
            educationalStatus,
        )?.let {
            applicationCommandGraduationInfoPort.delete(it)
        }
    }
}