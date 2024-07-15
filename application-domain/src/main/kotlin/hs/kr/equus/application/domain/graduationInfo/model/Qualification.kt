package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate
import java.time.YearMonth

data class Qualification(
    override val id: Long = 0,
    override val graduateDate: YearMonth? = null,
    override val isProspectiveGraduate: Boolean,
    override val receiptCode: Long,
) : GraduationInfo(
    graduateDate = graduateDate,
    isProspectiveGraduate = isProspectiveGraduate,
    receiptCode = receiptCode,
    id = id,
) {
    override fun hasEmptyInfo(): Boolean = graduateDate == null
}
