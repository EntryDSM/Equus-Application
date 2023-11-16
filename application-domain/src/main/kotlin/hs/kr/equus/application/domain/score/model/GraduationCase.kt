package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.global.annotation.Aggregate
import java.math.BigDecimal
import java.math.RoundingMode

@Aggregate
data class GraduationCase(
    val volunteerTime: Int,
    val absenceDayCount: Int,
    val lectureAbsenceCount: Int,
    val latenessCount: Int,
    val earlyLeaveCount: Int,
    val koreanGrade: String,
    val socialGrade: String,
    val historyGrade: String,
    val mathGrade: String,
    val scienceGrade: String,
    val englishGrade: String,
    val techAndHomeGrade: String,
    val isProspectiveGraduate: Boolean,
    val receiptCode: Long,
) : ApplicationCase(receiptCode) {
    operator fun BigDecimal.div(other: BigDecimal): BigDecimal = this.divide(other, 5, RoundingMode.HALF_EVEN)

    companion object {
        const val MAX_VOLUNTEER_TIME = 15
        const val MIN_VOLUNTEER_TIME = 1
        const val MAX_VOLUNTEER_SCORE = 15
        const val MIN_VOLUNTEER_SCORE = 0
        const val A_SCORE = 5
        const val B_SCORE = 4
        const val C_SCORE = 3
        const val D_SCORE = 2
        const val E_SCORE = 1

        // 학기별 인덱스
        const val THIRD_2BEFORE = 0
        const val THIRD_BEFORE = 1
        const val THIRD_GRADE_SECOND = 3
        const val THIRD_GRADE = 2 // 3학년 1학기 + 3학년 2학기
    }

    override fun calculateVolunteerScore(): BigDecimal {
        return if (volunteerTime >= MAX_VOLUNTEER_TIME) {
            BigDecimal(MAX_VOLUNTEER_SCORE)
        } else if (MIN_VOLUNTEER_TIME <= volunteerTime) {
            BigDecimal(volunteerTime)
        } else {
            BigDecimal(MIN_VOLUNTEER_SCORE)
        }
    }

    override fun calculateAttendanceScore(): Int {
        return (MAX_ATTENDANCE_SCORE - absenceDayCount - (lectureAbsenceCount + latenessCount + earlyLeaveCount) / 3)
            .coerceAtLeast(0)
    }

    override fun calculateGradeScores(): Array<BigDecimal> {
        val gradeScores: Array<BigDecimal> = calculateScores()
        for (semester in THIRD_2BEFORE..THIRD_GRADE) {
            gradeScores[semester] = gradeScores[semester].setScale(3, RoundingMode.HALF_UP)
        }
        return gradeScores
    }

    override fun calculateTotalGradeScore(isCommon: Boolean): BigDecimal {
        var totalGradeScore = BigDecimal.ZERO
        for (score in calculateScores()) {
            totalGradeScore += score
        }

        if (isCommon) {
            totalGradeScore *= COMMON_GRADE_RATE
        }

        return totalGradeScore.setScale(3, RoundingMode.HALF_UP)
    }

    private fun calculateScores(): Array<BigDecimal> {
        val scoresPerSemester = scoresPerSemester()
        val calculatedScores = arrayOf(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)

        for (semester in THIRD_2BEFORE..THIRD_BEFORE) {
            calculatedScores[semester] += scoresPerSemester[semester]
        }

        if (isProspectiveGraduate) {
            calculatedScores[2] = scoresPerSemester[2] * BigDecimal(2)
        } else {
            calculatedScores[2] = scoresPerSemester[2] + scoresPerSemester[3]
        }

        return checkShortOfSemesterCount(calculatedScores)
    }

    private fun scoresPerSemester(): Array<BigDecimal> {
        val gradesPerSemester = gradesPerSemester()
        val scoresPerSemester = arrayOf(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
        for (semester in THIRD_2BEFORE..THIRD_GRADE_SECOND) {
            scoresPerSemester[semester] = gradesToScore(gradesPerSemester[semester])
        }

        return scoresPerSemester
    }

    private fun gradesPerSemester(): Array<String> {
        val gradesPerSubjects =
            arrayOf(
                koreanGrade,
                socialGrade,
                historyGrade,
                mathGrade,
                scienceGrade,
                englishGrade,
                techAndHomeGrade,
            )
        val gradesPerSemester = arrayOf("", "", "", "")
        for (subjectGrades in gradesPerSubjects) {
            for (semester in THIRD_2BEFORE..THIRD_GRADE_SECOND) {
                gradesPerSemester[semester] = gradesPerSemester[semester].plus(subjectGrades[semester])
            }
        }
        return gradesPerSemester
    }

    private fun gradesToScore(gradesStr: String): BigDecimal {
        val grades = gradesStr.toCharArray()
        var semesterScore = 0
        var subjectCount = grades.size
        for (grade in grades) {
            when (grade) {
                'A' -> semesterScore += A_SCORE
                'B' -> semesterScore += B_SCORE
                'C' -> semesterScore += C_SCORE
                'D' -> semesterScore += D_SCORE
                'E' -> semesterScore += E_SCORE
                'X' -> subjectCount -= 1
            }
        }
        return if (semesterScore == 0) {
            BigDecimal.ZERO
        } else BigDecimal(semesterScore) / BigDecimal(subjectCount) * BigDecimal(4)
    }

    private fun checkShortOfSemesterCount(calculatedScores: Array<BigDecimal>): Array<BigDecimal> {
        if (calculatedScores[THIRD_BEFORE] == BigDecimal.ZERO &&
            calculatedScores[THIRD_2BEFORE] == BigDecimal.ZERO
        ) {
            for (semester in THIRD_2BEFORE..THIRD_BEFORE) {
                calculatedScores[semester] += calculatedScores[THIRD_GRADE] / BigDecimal(2)
            }
        } else if (calculatedScores[THIRD_2BEFORE] == BigDecimal.ZERO) {
            calculatedScores[THIRD_2BEFORE] =
                (calculatedScores[THIRD_GRADE] + calculatedScores[THIRD_BEFORE]) / BigDecimal(3)
        } else if (calculatedScores[THIRD_BEFORE] == BigDecimal.ZERO) {
            calculatedScores[THIRD_BEFORE] =
                (calculatedScores[THIRD_GRADE] + calculatedScores[THIRD_2BEFORE]) / BigDecimal(3)
        }
        return calculatedScores
    }
}
