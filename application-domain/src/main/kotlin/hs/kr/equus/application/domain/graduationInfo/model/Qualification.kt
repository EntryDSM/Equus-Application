package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

data class Qualification(
    val qualifiedDate: LocalDate,
    override val isProspectiveGraduate: Boolean,
    override val receiptCode: Long,
) : GraduationInfo(
        graduateDate = qualifiedDate,
        isProspectiveGraduate = isProspectiveGraduate,
        receiptCode = receiptCode,
    ) {
    override fun hasEmptyInfo(): Boolean = false
}
