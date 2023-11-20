package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import java.time.LocalDate

data class Graduation(
    override val graduateDate: LocalDate,
    override val isProspectiveGraduate: Boolean,
    override val receiptCode: Long = 0,
    val studentNumber: StudentNumber? = null,
    val schoolCode: String? = null,
) : GraduationInfo(
    graduateDate = graduateDate,
    isProspectiveGraduate,
    receiptCode,
) {
    override fun hasEmptyInfo(): Boolean = studentNumber == null || schoolCode == null
}
