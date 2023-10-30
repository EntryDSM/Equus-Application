package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

data class Qualification(
    val receiptCode: Long,
    val qualifiedDate: LocalDate,
    val isProspectiveGraduate: Boolean,
) : GraduationInfo(
        receiptCode,
        qualifiedDate,
        isProspectiveGraduate,
) {
    override fun hasEmptyInfo(): Boolean = false
}
