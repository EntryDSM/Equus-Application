package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

abstract class GraduationInfo(
    open val graduateDate: LocalDate,
    @get:JvmName("getIsProspectiveGraduate")
    open val isProspectiveGraduate: Boolean,
    open val receiptCode: Long,
) {
    val id: Long = 0
    abstract fun hasEmptyInfo(): Boolean
}
