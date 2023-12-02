package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.UpdateQualificationCaseRequest
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class UpdateQualificationCaseUseCase(
    private val securityPort: SecurityPort,
    private val applicationCaseQueryApplicationPort: ApplicationCaseQueryApplicationPort,
    private val commandApplicationCasePort: CommandApplicationCasePort,
    private val queryApplicationCasePort: QueryApplicationCasePort,
) {
    fun execute(request: UpdateQualificationCaseRequest) {
        val userId = securityPort.getCurrentUserId()
        val application = applicationCaseQueryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val qualificationCase =
            queryApplicationCasePort.queryApplicationCaseByApplication(application) as QualificationCase?
                ?: throw ApplicationCaseExceptions.EducationalStatusUnmatchedException()

        commandApplicationCasePort.save(
            qualificationCase.copy(
                averageScore = request.averageScore,
            ),
        )
    }
}