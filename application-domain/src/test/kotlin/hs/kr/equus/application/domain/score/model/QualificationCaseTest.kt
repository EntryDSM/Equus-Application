package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.global.annotation.EquusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@EquusTest
class QualificationCaseTest {
    private val qualificationCaseStub: QualificationCase by lazy {
        QualificationCase(
            averageScore = BigDecimal.valueOf(100),
            receiptCode = 1,
        )
    }

    @Test
    fun `출석점수 계산`() {
        // given
        val expectedScore = 15

        // when & then
        assertEquals(expectedScore, qualificationCaseStub.calculateAttendanceScore())
    }

    @Test
    fun `봉사점수 계산`() {
        // given
        val expectedScore = BigDecimal("15.000")

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
        val expectedScore = BigDecimal("80.000")

        // when & then
        assertEquals(expectedScore, qualificationCaseStub.calculateTotalGradeScore(false))
    }
}
