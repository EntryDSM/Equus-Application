package hs.kr.equus.application.domain.graduationInfo.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.applicationCase.service.ApplicationCaseService
import hs.kr.equus.application.domain.graduationInfo.factory.GraduationInfoFactory
import hs.kr.equus.application.domain.graduationInfo.service.GraduationInfoService

import hs.kr.equus.application.domain.graduationInfo.spi.CommandGraduationInfoPort
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.global.annotation.UseCase
import java.time.LocalDate
import java.time.YearMonth

@UseCase
class ChangeGraduationInfoUseCase(
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val graduationInfoQueryApplicationPort: GraduationInfoQueryApplicationPort,
    private val commandGraduationInfoPort: CommandGraduationInfoPort,
    private val graduationInfoFactory: GraduationInfoFactory,
    private val graduationInfoService: GraduationInfoService
) {
    fun execute(receiptCode: Long, graduateDate: YearMonth) {
        val application = graduationInfoQueryApplicationPort.queryApplicationByReceiptCode(receiptCode)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val existingInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(application)

        val newGraduationInfo = graduationInfoFactory.createGraduationInfo(
            receiptCode = receiptCode,
            educationalStatus = application.educationalStatus,
            graduateDate = graduateDate
        )

        when {
            existingInfo == null -> commandGraduationInfoPort.save(newGraduationInfo)
            graduationInfoService.hasEducationalStatusMismatch(application, existingInfo) -> {
                commandGraduationInfoPort.delete(existingInfo)
                commandGraduationInfoPort.save(newGraduationInfo)
            }
        }
    }
}
