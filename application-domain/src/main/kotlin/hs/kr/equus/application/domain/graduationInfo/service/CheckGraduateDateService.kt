package hs.kr.equus.application.domain.graduationInfo.service

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.global.annotation.DomainService
import java.time.LocalDate

@DomainService
class CheckGraduateDateService {
    fun checkIsInvalidYear(
        educationalStatus: EducationalStatus,
        graduateDate: LocalDate
    ) {
        var baseYear = LocalDate.now().year

        if (checkIsProspectiveGraduate(educationalStatus))
            baseYear += 1

        if (baseYear < graduateDate.year)
            throw GraduationInfoExceptions.InvalidGraduateDate()
    }

    private fun checkIsProspectiveGraduate(educationalStatus: EducationalStatus) =
        educationalStatus == EducationalStatus.PROSPECTIVE_GRADUATE
}
