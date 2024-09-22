package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.applicationCase.event.spi.ApplicationCaseEventPort
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.domain.applicationCase.model.vo.ExtraScoreItem
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
    private val qualificationEventPort: ApplicationCaseEventPort,
) {
    fun execute(request: UpdateQualificationCaseRequest) {
        val userId = securityPort.getCurrentUserId()
        val application = applicationCaseQueryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val qualificationCase =
            queryApplicationCasePort.queryApplicationCaseByApplication(application)

        if(qualificationCase !is QualificationCase) throw ApplicationCaseExceptions.EducationalStatusUnmatchedException()

        commandApplicationCasePort.save(
            qualificationCase.copy(
                koreanGrade = request.koreanGrade,
                scienceGrade = request.scienceGrade,
                socialGrade = request.socialGrade,
                mathGrade = request.mathGrade,
                englishGrade = request.englishGrade,
                optGrade = request.optGrade,
                extraScoreItem = ExtraScoreItem(
                    hasCertificate = request.extraScore.hasCertificate,
                    hasCompetitionPrize = request.extraScore.hasCompetitionPrize
                )
            ),
        )

        qualificationEventPort.updateApplicationCase(qualificationCase)
    }
}
