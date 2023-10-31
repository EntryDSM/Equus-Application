package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

data class Qualification(
    val receiptCode: Long,
    val qualifiedDate: LocalDate,
    val isProspectiveGraduate: Boolean,
) : GraduationInfo(
        qualifiedDate,
        isProspectiveGraduate,
        receiptCode,
    ) {
    override fun hasEmptyInfo(): Boolean = false
}
