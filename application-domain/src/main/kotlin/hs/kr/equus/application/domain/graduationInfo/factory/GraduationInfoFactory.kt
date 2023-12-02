package hs.kr.equus.application.domain.graduationInfo.factory

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo
import hs.kr.equus.application.domain.graduationInfo.model.Qualification
import hs.kr.equus.application.global.annotation.Factory

@Factory
class GraduationInfoFactory {
    fun createGraduationInfo(
        receiptCode: Long,
        educationalStatus: EducationalStatus?,
    ): GraduationInfo {
        return when (educationalStatus) {
            EducationalStatus.QUALIFICATION_EXAM ->
                Qualification(
                    receiptCode = receiptCode,
                    isProspectiveGraduate = false,
                )

            EducationalStatus.GRADUATE ->
                Graduation(
                    receiptCode = receiptCode,
                    isProspectiveGraduate = false,
                )

            EducationalStatus.PROSPECTIVE_GRADUATE ->
                Graduation(
                    receiptCode = receiptCode,
                    isProspectiveGraduate = true,
                )

            else ->
                throw ApplicationCaseExceptions.EducationalStatusUnmatchedException()
        }
    }
}
