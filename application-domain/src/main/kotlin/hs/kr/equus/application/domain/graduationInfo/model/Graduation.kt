package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import java.time.LocalDate

data class Graduation(
    val graduateDate: LocalDate,
    val isProspectiveGraduate: Boolean,
    val studentNumber: StudentNumber? = null,
    val schoolCode: String? = null,
    val receiptCode: Long = 0,
) : GraduationInfo(
        graduateDate,
        isProspectiveGraduate,
        receiptCode,
    ) {
    override fun hasEmptyInfo(): Boolean = studentNumber == null || schoolCode == null
}
