package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.global.annotation.EquusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@EquusTest
class GraduationCaseTest {
    private val graduationCaseStub: GraduationCase by lazy {
        GraduationCase(
            volunteerTime = 15,
            absenceDayCount = 0,
            lectureAbsenceCount = 0,
            latenessCount = 0,
            earlyLeaveCount = 0,
            koreanGrade = "ABCD",
            socialGrade = "ABCD",
            historyGrade = "ABCD",
            mathGrade = "ABCD",
            scienceGrade = "ABCD",
            englishGrade = "ABCD",
            techAndHomeGrade = "ABCD",
            isProspectiveGraduate = true,
            receiptCode = 1,
        )
    }

    @Test
    fun `출석점수 계산`() {
        // given
        val graduationCase = graduationCaseStub.copy(
            absenceDayCount = 4,
            lectureAbsenceCount = 4,
            latenessCount = 4,
            earlyLeaveCount = 4,
        )
        // when & then
        assertEquals(7, graduationCase.calculateAttendanceScore())
    }

    @Test
    fun `봉사점수 계산`() {
        // given
        val graduationCase = graduationCaseStub.copy(
            volunteerTime = 13
        )
        // when & then
        assertEquals(BigDecimal(13), graduationCase.calculateVolunteerScore())
    }

    @Test
    fun `졸업 교과점수계산`() {
        // given
        val graduationCase = graduationCaseStub.copy(
            isProspectiveGraduate = false,
        )

        val expectedGradeScores = arrayOf(
            BigDecimal("20.000"),
            BigDecimal("16.000"),
            BigDecimal("20.000"),
        )
        // when
        val gradeScores = graduationCase.calculateGradeScores()

        // then
        for (i in 0..2) {
            assertEquals(expectedGradeScores[i], gradeScores[i])
        }
    }

    @Test
    fun `졸업예정 교과점수계산`() {
        val graduationCase = graduationCaseStub.copy(
            koreanGrade = "ABCX",
            socialGrade = "ABCX",
            historyGrade = "ABCX",
            mathGrade = "ABCX",
            scienceGrade = "ABCX",
            englishGrade = "ABCX",
            techAndHomeGrade = "ABCX",
        )
        val expectedGradeScores = arrayOf(
            BigDecimal("20.000"),
            BigDecimal("16.000"),
            BigDecimal("24.000"),
        )

        // when
        val gradeScores = graduationCase.calculateGradeScores()

        // then
        for (i in 0..2) {
            assertEquals(expectedGradeScores[i], gradeScores[i])
        }
    }

    @Test
    fun `일반전형 총교과점수 계산`() {
        // given
        val graduationCase = graduationCaseStub.copy(
            koreanGrade = "AAAA",
            socialGrade = "AAAA",
            historyGrade = "AAAA",
            mathGrade = "AAAA",
            scienceGrade = "AAAA",
            englishGrade = "AAAA",
            techAndHomeGrade = "AAAA",
        )
        val expectedTotalGradeScore = BigDecimal("140.000")

        // when & then
        assertEquals(expectedTotalGradeScore, graduationCase.calculateTotalGradeScore(true))
    }

    @Test
    fun `특별전형 총교과점수 계산`() {
        // given
        val graduationCase = graduationCaseStub.copy(
            koreanGrade = "AAAA",
            socialGrade = "AAAA",
            historyGrade = "AAAA",
            mathGrade = "AAAA",
            scienceGrade = "AAAA",
            englishGrade = "AAAA",
            techAndHomeGrade = "AAAA",
        )
        val expectedTotalGradeScore = BigDecimal("80.000")

        // when & then
        assertEquals(expectedTotalGradeScore, graduationCase.calculateTotalGradeScore(false))
    }

    @Test
    fun `3학년 학기만 있을때 교과점수계산`() {
        // given
        val graduationCase = graduationCaseStub.copy(
            koreanGrade = "XXAA",
            socialGrade = "XXAA",
            historyGrade = "XXAA",
            mathGrade = "XXAA",
            scienceGrade = "XXAA",
            englishGrade = "XXAA",
            techAndHomeGrade = "XXAA",
            isProspectiveGraduate = false,
        )
        val expectedTotalGradeScore = BigDecimal("20.000")

        // when
        val gradeScores = graduationCase.calculateGradeScores()

        // then
        assertEquals(expectedTotalGradeScore, gradeScores[0])
        assertEquals(expectedTotalGradeScore, gradeScores[1])
    }

    @Test
    fun `직전전 학기 없을때 교과점수계산`() {
        // given
        val graduationCase = graduationCaseStub.copy(
            koreanGrade = "XACB",
            socialGrade = "XACB",
            historyGrade = "XACB",
            mathGrade = "XACB",
            scienceGrade = "XACB",
            englishGrade = "XACB",
            techAndHomeGrade = "XACB",
            isProspectiveGraduate = false,
        )
        val expectedGradeScore = BigDecimal("16.000")

        // when & then
        assertEquals(expectedGradeScore, graduationCase.calculateGradeScores()[0])
    }

    @Test
    fun `직전학기 없을때 교과점수계산`() {
        // given
        val graduationCase = graduationCaseStub.copy(
            koreanGrade = "AXBC",
            socialGrade = "AXBC",
            historyGrade = "AXBC",
            mathGrade = "AXBC",
            scienceGrade = "AXBC",
            englishGrade = "AXBC",
            techAndHomeGrade = "AXBC",
            isProspectiveGraduate = false,
        )
        val expectedGradeScore = BigDecimal("16.000")

        // when & then
        assertEquals(expectedGradeScore, graduationCase.calculateGradeScores()[1])
    }

    @Test
    fun `안배운 과목이 있을때 과목점수계산`() {
        // given
        val graduationCase = graduationCaseStub.copy(
            koreanGrade = "AABC",
            socialGrade = "AXBC",
            historyGrade = "AAXC",
            mathGrade = "AABC",
            scienceGrade = "AABC",
            englishGrade = "AABC",
            techAndHomeGrade = "AABC",
            isProspectiveGraduate = false,
        )

        val expectedGradeScores = arrayOf(
            BigDecimal("20.000"),
            BigDecimal("20.000"),
            BigDecimal("28.000"),
        )

        // when
        val gradeScores = graduationCase.calculateGradeScores()

        // then
        for (i in 0..2) {
            assertEquals(expectedGradeScores[i], gradeScores[i])
        }
    }
}
