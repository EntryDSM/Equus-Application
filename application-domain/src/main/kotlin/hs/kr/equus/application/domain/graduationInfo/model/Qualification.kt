package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

data class Qualification(
    override val id: Long = 0,
    override val graduateDate: LocalDate? = null,
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
