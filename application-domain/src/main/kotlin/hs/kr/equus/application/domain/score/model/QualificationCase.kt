package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.global.annotation.Aggregate
import java.math.BigDecimal
import java.math.RoundingMode

@Aggregate
data class QualificationCase(
    val averageScore: BigDecimal,
    val receiptCode: Long,
) : ApplicationCase(receiptCode) {
    override fun calculateVolunteerScore(): BigDecimal {
        return (averageScore - BigDecimal(40))
            .divide(BigDecimal(4), 3, RoundingMode.HALF_UP)
    }

    override fun calculateAttendanceScore(): Int {
        return MAX_ATTENDANCE_SCORE
    }

    override fun calculateGradeScores(): Array<BigDecimal> {
        return arrayOf(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
    }

    override fun calculateTotalGradeScore(isCommon: Boolean): BigDecimal {
        val totalGradeScore =
            ((averageScore - BigDecimal(50)) * BigDecimal.valueOf(1.6))
                .setScale(3, RoundingMode.HALF_UP)

        return if (isCommon) {
            (totalGradeScore * (COMMON_GRADE_RATE))
                .setScale(3, RoundingMode.HALF_UP)
        } else totalGradeScore
    }
}
