package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.usecase.dto.response.GetGraduationCaseResponse
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class GetGraduationCaseUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val queryApplicationCasePort: QueryApplicationCasePort
) {
    fun execute(): GetGraduationCaseResponse {
        val userId = securityPort.getCurrentUserId()

        val application = queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val graduationCase = queryApplicationCasePort.queryApplicationCaseByApplication(application) as GraduationCase?
            ?: throw ApplicationCaseExceptions.EducationalStatusUnmatchedException()

        return graduationCase.run {
            GetGraduationCaseResponse(
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
            )
        }
    }
}