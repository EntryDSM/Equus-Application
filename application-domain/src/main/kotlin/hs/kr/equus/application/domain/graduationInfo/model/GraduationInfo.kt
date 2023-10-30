package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

abstract class GraduationInfo(
    receiptCode: Long,
    graduationDate: LocalDate,
    isProspectiveGraduate: Boolean,
) {
    abstract fun hasEmptyInfo(): Boolean
}
