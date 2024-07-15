package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate
import java.time.YearMonth

sealed class GraduationInfo(
    open val id: Long,
    open val graduateDate: YearMonth?,
    @get:JvmName("getIsProspectiveGraduate")
    open val isProspectiveGraduate: Boolean,
    open val receiptCode: Long,
) {
    abstract fun hasEmptyInfo(): Boolean
}
