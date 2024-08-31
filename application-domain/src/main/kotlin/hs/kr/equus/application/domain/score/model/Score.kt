package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.global.annotation.Aggregate
import java.math.BigDecimal

@Aggregate
data class Score(
    val id: Long = 0,
    val attendanceScore: Int? = null,
    val volunteerScore: BigDecimal? = null,
    val thirdBeforeBeforeScore: BigDecimal? = null,
    val thirdBeforeScore: BigDecimal? = null,
    val thirdGradeScore: BigDecimal? = null,
    val thirdScore: BigDecimal? = null,
    val totalGradeScore: BigDecimal? = null,
    val totalScore: BigDecimal? = null,
    val extraScore: BigDecimal? = null,
    val receiptCode: Long,
) {
    fun updateScore(
        applicationCase: ApplicationCase,
        isCommon: Boolean,
        extraScore: BigDecimal
    ): Score {
        val attendanceScore = applicationCase.calculateAttendanceScore()
        val volunteerScore = applicationCase.calculateVolunteerScore()
        val gradeScores = applicationCase.calculateGradeScores()
        val totalGradeScore = applicationCase.calculateTotalGradeScore(isCommon)
        return copy(
            attendanceScore = attendanceScore,
            volunteerScore = volunteerScore,
            thirdScore = gradeScores[3],
            thirdGradeScore = gradeScores[2],
            thirdBeforeScore = gradeScores[1],
            thirdBeforeBeforeScore = gradeScores[0],
            totalGradeScore = totalGradeScore,
            totalScore = totalGradeScore + BigDecimal(attendanceScore) + volunteerScore + extraScore,
            extraScore = extraScore
        )
    }

    fun calculateSubjectScore(): BigDecimal {
        return (thirdGradeScore ?: BigDecimal.ZERO) +
                (thirdBeforeScore ?: BigDecimal.ZERO) +
                (thirdBeforeBeforeScore ?: BigDecimal.ZERO)
    }
}