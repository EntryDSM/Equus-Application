package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

abstract class GraduationInfo(
    graduationDate: LocalDate,
    isProspectiveGraduate: Boolean,
    receiptCode: Long,
) {
    val id: Long = 0

    abstract fun hasEmptyInfo(): Boolean
}
