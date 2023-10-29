package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import java.time.LocalDate

data class Graduation(
    val receiptCode: Long,
    val graduateDate: LocalDate,
    val educationalStatus: EducationalStatus,
    val studentNumber: StudentNumber? = null,
    val schoolCode: String? = null
) : GraduationInfo(
    receiptCode,
) {
    override fun hasEmptyInfo(): Boolean =
        studentNumber == null || schoolCode == null


    override fun isProspectiveGraduate(): Boolean =
        educationalStatus == EducationalStatus.PROSPECTIVE_GRADUATE
}
