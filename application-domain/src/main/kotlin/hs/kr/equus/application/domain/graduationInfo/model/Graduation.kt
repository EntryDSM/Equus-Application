package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import java.time.LocalDate
import java.time.YearMonth

data class Graduation(
    override val id: Long = 0,
    override val graduateDate: YearMonth? = null,
    override val isProspectiveGraduate: Boolean,
    override val receiptCode: Long,
    val studentNumber: StudentNumber? = null,
    val schoolCode: String? = null,
    val teacherName: String? = null,
    val teacherTel: String? = null
) : GraduationInfo(
    graduateDate = graduateDate,
    isProspectiveGraduate = isProspectiveGraduate,
    receiptCode = receiptCode,
    id = id,
) {
    override fun hasEmptyInfo(): Boolean {
        return listOf(
            graduateDate,
            schoolCode,
            studentNumber,
            teacherName,
            teacherTel
        ).any { it == null }
    }
}
