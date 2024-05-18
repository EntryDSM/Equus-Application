package hs.kr.equus.application.domain.graduationInfo.model

import java.time.LocalDate

sealed class GraduationInfo(
    open val id: Long,
    open val graduateDate: LocalDate?,
    @get:JvmName("getIsProspectiveGraduate")
    open val isProspectiveGraduate: Boolean,
    open val receiptCode: Long,
) {
    abstract fun hasEmptyInfo(): Boolean
}
