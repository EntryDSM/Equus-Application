package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.global.annotation.Aggregate
import java.math.BigDecimal
import java.math.RoundingMode

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
            thirdScore = gradeScores[0],
            thirdGradeScore = gradeScores[1],
            thirdBeforeScore = gradeScores[2],
            thirdBeforeBeforeScore = gradeScores[3],
            totalGradeScore = totalGradeScore,
            totalScore  = totalGradeScore.add(BigDecimal(attendanceScore)).add(volunteerScore).add(extraScore).setScale(3, RoundingMode.HALF_UP),
            extraScore = extraScore
        )
    }

    fun calculateByIsCommonAndExtraScore(
        applicationCase: ApplicationCase,
        isCommon: Boolean,
        calculateCompetitionScore: Boolean,
        calculateCertificateScore: Boolean
    ): BigDecimal {
        val attendanceScore = applicationCase.calculateAttendanceScore()
        val volunteerScore = applicationCase.calculateVolunteerScore()
        val totalGradeScore = applicationCase.calculateTotalGradeScore(isCommon)
        val certificateScore = if(calculateCertificateScore) applicationCase.calculateCertificateScore() else BigDecimal(0)
        val competitionScore = if(calculateCompetitionScore) applicationCase.calculateCompetitionScore() else BigDecimal(0)

        return totalGradeScore
            .add(BigDecimal(attendanceScore))
            .add(volunteerScore)
            .add(certificateScore)
            .add(competitionScore)
    }
    fun calculateSubjectScore(): BigDecimal {
        return (thirdGradeScore ?: BigDecimal.ZERO) +
                (thirdBeforeScore ?: BigDecimal.ZERO) +
                (thirdBeforeBeforeScore ?: BigDecimal.ZERO) +
                (thirdScore ?: BigDecimal.ZERO)
    }

}