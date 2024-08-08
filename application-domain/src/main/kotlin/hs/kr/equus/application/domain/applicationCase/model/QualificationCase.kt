package hs.kr.equus.application.domain.applicationCase.model

import hs.kr.equus.application.domain.applicationCase.model.vo.ExtraScoreItem
import hs.kr.equus.application.global.annotation.Aggregate
import java.math.BigDecimal
import java.math.RoundingMode

@Aggregate
data class QualificationCase(
    override val id: Long = 0,
    override val receiptCode: Long,
    override val extraScoreItem: ExtraScoreItem = ExtraScoreItem(false, false),
    val koreanGrade: BigDecimal = BigDecimal.ZERO,
    val socialGrade: BigDecimal = BigDecimal.ZERO,
    val mathGrade: BigDecimal = BigDecimal.ZERO,
    val scienceGrade: BigDecimal = BigDecimal.ZERO,
    val englishGrade: BigDecimal = BigDecimal.ZERO,
    val optGrade: BigDecimal = BigDecimal.ZERO,
    // 일반전형 여부
    val isCommon: Boolean = true,
) : ApplicationCase(
    id = id,
    receiptCode = receiptCode,
    extraScoreItem = extraScoreItem
) {

    // 과목 등급 나누기
    private fun getScorePoint(grade: BigDecimal): Int {
        return when {
            grade >= BigDecimal(98) -> 5
            grade >= BigDecimal(94) -> 4
            grade >= BigDecimal(90) -> 3
            grade >= BigDecimal(86) -> 2
            else -> 1
        }
    }

    fun calculateAverageScore(): BigDecimal {
        val totalPoints =
                    optGrade +
                    socialGrade +
                    mathGrade +
                    englishGrade +
                    scienceGrade +
                    koreanGrade

        return totalPoints.divide(BigDecimal(6), 3, RoundingMode.HALF_UP)
    }

    // 과목 등급점수 평균 구하기
    private fun calculatePointAverageScore(): BigDecimal {
        val totalPoints = listOf(
            getScorePoint(koreanGrade),
            getScorePoint(socialGrade),
            getScorePoint(mathGrade),
            getScorePoint(scienceGrade),
            getScorePoint(englishGrade),
            getScorePoint(optGrade)
        ).sum()
        return BigDecimal(totalPoints).divide(BigDecimal(6), 3, RoundingMode.HALF_UP)
    }

    // 검정고시에서는 X
    override fun calculateVolunteerScore(): BigDecimal {
        return BigDecimal.ZERO
    }

    // 검정고시에서는 X
    override fun calculateAttendanceScore(): Int {
        return 0
    }

    // 검정고시에서는 X
    override fun calculateGradeScores(): Array<BigDecimal> {
        return arrayOf(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
    }

    // 일반전형 → 평균 * 34 + 알고리즘 대회(3)  = 173
    //그 외 전형 → 평균 * 22 + 알고리즘 대회(3) + 자격증 취득(6) = 119
    override fun calculateTotalGradeScore(isCommon: Boolean): BigDecimal {
        val averageScore = calculatePointAverageScore()
        return if (isCommon) {
            (averageScore * BigDecimal(34) + BigDecimal(3)).setScale(3, RoundingMode.HALF_UP)
        } else {
            (averageScore * BigDecimal(22) + BigDecimal(3) + BigDecimal(6)).setScale(3, RoundingMode.HALF_UP)
        }
    }
}
