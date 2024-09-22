package hs.kr.equus.application.domain.applicationCase.model

import hs.kr.equus.application.domain.applicationCase.model.vo.ExtraScoreItem
import hs.kr.equus.application.global.annotation.Aggregate
import java.math.BigDecimal
import java.math.RoundingMode

@Aggregate
data class GraduationCase(
    override val id: Long = 0,
    override val receiptCode: Long,
    override val extraScoreItem: ExtraScoreItem = ExtraScoreItem(false, false),
    val volunteerTime: Int = 0,
    val absenceDayCount: Int = 0,
    val lectureAbsenceCount: Int = 0,
    val latenessCount: Int = 0,
    val earlyLeaveCount: Int = 0,
    val koreanGrade: String = "XXXX",
    val socialGrade: String = "XXXX",
    val historyGrade: String = "XXXX",
    val mathGrade: String = "XXXX",
    val scienceGrade: String = "XXXX",
    val englishGrade: String = "XXXX",
    val techAndHomeGrade: String = "XXXX",
    @get:JvmName("getIsProspectiveGraduate")
    val isProspectiveGraduate: Boolean,
) : ApplicationCase(
    id = id,
    receiptCode = receiptCode,
    extraScoreItem = extraScoreItem
) {
    operator fun BigDecimal.div(other: BigDecimal): BigDecimal = this.divide(other, 5, RoundingMode.HALF_EVEN)

    companion object {
        private const val MAX_VOLUNTEER_TIME = 15
        private const val MIN_VOLUNTEER_TIME = 1
        private const val MAX_VOLUNTEER_SCORE = 15
        private const val MIN_VOLUNTEER_SCORE = 0
        private const val A_SCORE = 5
        private const val B_SCORE = 4
        private const val C_SCORE = 3
        private const val D_SCORE = 2
        private const val E_SCORE = 1

        // 학기별 인덱스
        private const val THIRD_2BEFORE = 3 // 직전전학기
        private const val THIRD_BEFORE = 2 // 직전학기
        private const val THIRD_GRADE_FIRST = 1 // 3학년 1학기
        private const val THIRD_GRADE_SECOND = 0 // 3학년 2학기
    }

    fun calculateAdditionalScore(isCommon: Boolean): BigDecimal {
        val competitionPrize = if (extraScoreItem.hasCompetitionPrize) BigDecimal(3) else BigDecimal.ZERO
        val certificate = if (extraScoreItem.hasCertificate) BigDecimal(6) else BigDecimal.ZERO
        return if (isCommon) certificate else competitionPrize + certificate
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
        for (semester in THIRD_GRADE_SECOND..THIRD_2BEFORE) {
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
        val calculatedScores = arrayOf(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)

        for (semester in THIRD_BEFORE..THIRD_2BEFORE) {
            calculatedScores[semester] += scoresPerSemester[semester]
        }

        if (isProspectiveGraduate) { // todo 오버라이딩에 맡추기 위해 isProspectiveGraduate를 파라미터가 아닌 필드로 저장함. 후에 변경 요함
            calculatedScores[THIRD_GRADE_FIRST] = scoresPerSemester[THIRD_GRADE_FIRST] * BigDecimal(2)
        } else {
            calculatedScores[THIRD_GRADE_FIRST] = scoresPerSemester[THIRD_GRADE_FIRST]
            calculatedScores[THIRD_GRADE_SECOND] = scoresPerSemester[THIRD_GRADE_SECOND]
        }
        return checkShortOfSemesterCount(calculatedScores)
    }

    private fun scoresPerSemester(): Array<BigDecimal> {
        val gradesPerSemester = gradesPerSemester()
        val scoresPerSemester = arrayOf(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO)
        for (semester in THIRD_GRADE_SECOND..THIRD_2BEFORE) {
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
            for (semester in THIRD_GRADE_SECOND..THIRD_2BEFORE) {
                gradesPerSemester[semester] = gradesPerSemester[semester].plus(subjectGrades[semester])
            }
        }
        return gradesPerSemester
    }

    fun gradesPerSubject(): Map<String, Array<String>> {
        val subjectNames = arrayOf(
            "국어", "사회", "역사", "수학", "과학", "영어", "기술가정"
        )
        val subjects = arrayOf(
            koreanGrade,
            socialGrade,
            historyGrade,
            mathGrade,
            scienceGrade,
            englishGrade,
            techAndHomeGrade
        )

        return subjectNames.zip(subjects).associate { (name, grades) ->
            name to (grades.takeIf { it.isNotEmpty() }?.toCharArray()?.map { it.toString() }?.toTypedArray() ?: arrayOf("X"))
        }
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
        } else {
            BigDecimal(semesterScore) / BigDecimal(subjectCount) * BigDecimal(4)
        }
    }

    private fun checkShortOfSemesterCount(calculatedScores: Array<BigDecimal>): Array<BigDecimal> {
        if (calculatedScores[THIRD_BEFORE] == BigDecimal.ZERO &&
            calculatedScores[THIRD_2BEFORE] == BigDecimal.ZERO
        ) {
            for (semester in THIRD_BEFORE.. THIRD_2BEFORE) {
                calculatedScores[semester] += calculatedScores[THIRD_GRADE_FIRST] / BigDecimal(2)
            }
        } else if (calculatedScores[THIRD_2BEFORE] == BigDecimal.ZERO) {
            calculatedScores[THIRD_2BEFORE] =
                (calculatedScores[THIRD_GRADE_FIRST] + calculatedScores[THIRD_BEFORE]) / BigDecimal(3)
        } else if (calculatedScores[THIRD_BEFORE] == BigDecimal.ZERO) {
            calculatedScores[THIRD_BEFORE] =
                (calculatedScores[THIRD_GRADE_FIRST] + calculatedScores[THIRD_2BEFORE]) / BigDecimal(3)
        }
        return calculatedScores
    }
}
