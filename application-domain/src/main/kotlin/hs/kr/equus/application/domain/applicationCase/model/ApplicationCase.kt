package hs.kr.equus.application.domain.applicationCase.model

import hs.kr.equus.application.domain.applicationCase.model.vo.ExtraScoreItem
import java.math.BigDecimal

sealed class ApplicationCase(
    open val receiptCode: Long,
    open val id: Long,
    open val extraScoreItem: ExtraScoreItem = ExtraScoreItem(false, false)
) {
    companion object {
        const val MAX_ATTENDANCE_SCORE = 15
        val COMMON_GRADE_RATE: BigDecimal = BigDecimal.valueOf(1.75)
    }

    abstract fun calculateVolunteerScore(): BigDecimal

    abstract fun calculateAttendanceScore(): Int

    abstract fun calculateGradeScores(): Array<BigDecimal>

    abstract fun calculateTotalGradeScore(isCommon: Boolean): BigDecimal

    abstract fun calculateCertificateScore(): BigDecimal

    abstract fun calculateCompetitionScore(): BigDecimal
}
