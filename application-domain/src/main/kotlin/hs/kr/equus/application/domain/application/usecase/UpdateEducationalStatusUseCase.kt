package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.event.spi.ApplicationEventPort
import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.spi.ApplicationQueryScorePort
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateEducationalStatusRequest
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.spi.CommandGraduationInfoPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.domain.score.exception.ScoreExceptions
import hs.kr.equus.application.domain.score.spi.CommandScorePort
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

@UseCase
class UpdateEducationalStatusUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
    private val applicationEventPort: ApplicationEventPort,
    private val commandApplicationCasePort: CommandApplicationCasePort,
    private val queryApplicationCasePort: QueryApplicationCasePort,
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val commandGraduationInfoPort: CommandGraduationInfoPort
) {
    fun execute(request: UpdateEducationalStatusRequest) {
        val userId = securityPort.getCurrentUserId()
        val application =
            queryApplicationPort.queryApplicationByUserId(userId)
                ?: throw ApplicationExceptions.ApplicationNotFoundException()

        if (isEducationalStatusChanged(application.educationalStatus, request.educationalStatus)) {
            if (!isPermissibleStatusTransition(application.educationalStatus, request.educationalStatus)) {
                deleteGraduationInfoAndApplicationCase(application)
            }
        }

        commandApplicationPort.save(
            application.copy(
                educationalStatus = request.educationalStatus,
            )
        )

        TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
            override fun afterCommit() {
                applicationEventPort.updateEducationalStatus(application.receiptCode, request.graduateDate)
            }
        })
    }

    private fun isEducationalStatusChanged(
        applicationStatus: EducationalStatus?,
        requestStatus: EducationalStatus
    ): Boolean {
        applicationStatus ?: return false
        return applicationStatus != requestStatus
    }

    private fun isPermissibleStatusTransition(applicationStatus: EducationalStatus?, requestStatus: EducationalStatus): Boolean {
        return (applicationStatus == EducationalStatus.PROSPECTIVE_GRADUATE && requestStatus == EducationalStatus.GRADUATE) ||
                (applicationStatus == EducationalStatus.GRADUATE && requestStatus == EducationalStatus.PROSPECTIVE_GRADUATE)
    }

    private fun deleteGraduationInfoAndApplicationCase(application: Application) {
        val applicationCase = queryApplicationCasePort.queryApplicationCaseByApplication(application)
            ?: throw ApplicationCaseExceptions.ApplicationCaseNotFoundException()
        commandApplicationCasePort.delete(applicationCase)

        val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(application)
            ?: throw GraduationInfoExceptions.GraduationNotFoundException()
        commandGraduationInfoPort.delete(graduationInfo)
    }
}
