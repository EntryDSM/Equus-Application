package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.applicationCase.event.spi.ApplicationCaseEventPort
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.vo.ExtraScoreItem
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.UpdateGraduationCaseRequest
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class UpdateGraduationCaseUseCase(
    private val securityPort: SecurityPort,
    private val applicationCaseQueryApplicationPort: ApplicationCaseQueryApplicationPort,
    private val commandApplicationCasePort: CommandApplicationCasePort,
    private val queryApplicationCasePort: QueryApplicationCasePort,
    private val graduationCaseEventPort: ApplicationCaseEventPort,
) {
    fun execute(request: UpdateGraduationCaseRequest) {
        val userId = securityPort.getCurrentUserId()
        val application = applicationCaseQueryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val graduationCase =
            queryApplicationCasePort.queryApplicationCaseByApplication(application)

        if(graduationCase !is GraduationCase) throw ApplicationCaseExceptions.EducationalStatusUnmatchedException()

        request.run {
            commandApplicationCasePort.save(
                graduationCase.copy(
                    volunteerTime = volunteerTime,
                    absenceDayCount = absenceDayCount,
                    lectureAbsenceCount = lectureAbsenceCount,
                    latenessCount = latenessCount,
                    earlyLeaveCount = earlyLeaveCount,
                    koreanGrade = koreanGrade,
                    socialGrade = socialGrade,
                    historyGrade = historyGrade,
                    mathGrade = mathGrade,
                    scienceGrade = scienceGrade,
                    englishGrade = englishGrade,
                    techAndHomeGrade = techAndHomeGrade,
                    extraScoreItem = ExtraScoreItem(
                        hasCertificate = extraScore.hasCertificate,
                        hasCompetitionPrize = extraScore.hasCompetitionPrize
                    )
                ),
            )
        }

        graduationCaseEventPort.updateGraduationCase(graduationCase)
    }
}
