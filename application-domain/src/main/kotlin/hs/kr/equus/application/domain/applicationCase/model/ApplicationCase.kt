package hs.kr.equus.application.domain.applicationCase.model

import java.math.BigDecimal

abstract class ApplicationCase(
    receiptCode: Long,
) {
    val id: Long = 0

    companion object {
        const val MAX_ATTENDANCE_SCORE = 15
        val COMMON_GRADE_RATE: BigDecimal = BigDecimal.valueOf(1.75)
    }

    abstract fun calculateVolunteerScore(): BigDecimal

    abstract fun calculateAttendanceScore(): Int

    abstract fun calculateGradeScores(): Array<BigDecimal>

    abstract fun calculateTotalGradeScore(isCommon: Boolean): BigDecimal
}
