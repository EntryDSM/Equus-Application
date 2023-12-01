package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

data class Qualification(
    override val graduateDate: LocalDate? = null,
    override val isProspectiveGraduate: Boolean,
    override val receiptCode: Long,
) : GraduationInfo(
    graduateDate = graduateDate,
    isProspectiveGraduate = isProspectiveGraduate,
    receiptCode = receiptCode,
) {
    override fun hasEmptyInfo(): Boolean = false
}
