package hs.kr.equus.application.domain.graduationInfo.factory

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo
import hs.kr.equus.application.domain.graduationInfo.model.Qualification
import hs.kr.equus.application.global.annotation.Factory
import java.time.LocalDate

@Factory
class GraduationInfoFactory {
    fun createGraduationInfo(
        receiptCode: Long,
        educationalStatus: EducationalStatus,
        graduationDate: LocalDate,
    ): GraduationInfo {
        when (educationalStatus) {
            EducationalStatus.QUALIFICATION_EXAM ->
                return Qualification(
                    receiptCode = receiptCode,
                    qualifiedDate = graduationDate,
                    isProspectiveGraduate = false
                )

            EducationalStatus.GRADUATE ->
                return Graduation(
                    receiptCode = receiptCode,
                    graduateDate = graduationDate,
                    isProspectiveGraduate = false,
                )

            EducationalStatus.PROSPECTIVE_GRADUATE ->
                return Graduation(
                    receiptCode = receiptCode,
                    graduateDate = graduationDate,
                    isProspectiveGraduate = true,
                )
        }
    }
}