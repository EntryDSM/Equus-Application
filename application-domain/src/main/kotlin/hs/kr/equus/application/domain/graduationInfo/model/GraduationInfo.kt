package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

abstract class GraduationInfo(
    graduateDate: LocalDate,
    isProspectiveGraduate: Boolean,
    receiptCode: Long,
) {
    val id: Long = 0
    abstract fun hasEmptyInfo(): Boolean
}
