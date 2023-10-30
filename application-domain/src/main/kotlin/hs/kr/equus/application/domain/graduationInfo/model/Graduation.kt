package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import java.time.LocalDate

data class Graduation(
    val receiptCode: Long,
    val graduateDate: LocalDate,
    val isProspectiveGraduate: Boolean,
    val studentNumber: StudentNumber? = null,
    val schoolCode: String? = null,
) : GraduationInfo(
        receiptCode,
        graduateDate,
        isProspectiveGraduate,
) {
    override fun hasEmptyInfo(): Boolean = studentNumber == null || schoolCode == null
}
