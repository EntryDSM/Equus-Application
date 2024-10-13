package hs.kr.equus.application.domain.applicationCase

import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.global.annotation.EquusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@EquusTest
class QualificationCaseTest {
    private val qualificationCaseStub: QualificationCase by lazy {
        QualificationCase(
            receiptCode = 1,
            koreanGrade = BigDecimal(90.0),
            socialGrade = BigDecimal(90.0),
            mathGrade = BigDecimal(90.0),
            scienceGrade = BigDecimal(90.0),
            englishGrade = BigDecimal(90.0),
            optGrade = BigDecimal(90.0)
        )
    }

    @Test
    fun `출석점수 계산`() {
        // given
        val expectedScore = 0

        // when & then
        assertEquals(expectedScore, qualificationCaseStub.calculateAttendanceScore())
    }

    @Test
    fun `봉사점수 계산`() {
        // given
        val expectedScore = BigDecimal("0")

        // when & then
        assertEquals(expectedScore, qualificationCaseStub.calculateVolunteerScore())
    }

    @Test
    fun `교과점수 계산`() {
        // given
        val expectedScores =
            arrayOf(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
            )

        // when
        val gradeScore = qualificationCaseStub.calculateGradeScores()

        // then
        for (i in 0..3) {
            assertEquals(expectedScores[i], gradeScore[i])
        }
    }

    @Test
    fun `일반전형 교과총점수 계산`() {
        // given
        val expectedScore = BigDecimal("140.000")

        // when & then
        assertEquals(expectedScore, qualificationCaseStub.calculateTotalGradeScore(true))
    }

    @Test
    fun `특별전형 교과총점수 계산`() {
        // given
        val expectedScore = BigDecimal("66.000")

        // when & then
        assertEquals(expectedScore, qualificationCaseStub.calculateTotalGradeScore(false))
    }
}
