package hs.kr.equus.application.domain.graduationInfo.service

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.global.annotation.EquusTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

@EquusTest
class CheckGraduateDateServiceTest {

    private val checkGraduateDateService = CheckGraduateDateService()

    private val graduateDate = LocalDate.now()

    private val nextYear = LocalDate.now().plusYears(1)

    private val laterYears = nextYear.plusYears(1)

    @Test
    fun `올바른 졸업일`() {
        assertDoesNotThrow {
            checkGraduateDateService.checkIsInvalidYear(
                educationalStatus = EducationalStatus.GRADUATE,
                graduateDate = graduateDate,
            )
        }
    }

    @Test
    fun `졸업으로 연도가 잘못된 경우`() {
        assertThrows<GraduationInfoExceptions.InvalidGraduateDate> {
            checkGraduateDateService.checkIsInvalidYear(
                educationalStatus = EducationalStatus.GRADUATE,
                graduateDate = nextYear,
            )
        }
    }

    @Test
    fun `졸업예정 내년으로 성공`() {
        assertDoesNotThrow {
            checkGraduateDateService.checkIsInvalidYear(
                educationalStatus = EducationalStatus.PROSPECTIVE_GRADUATE,
                graduateDate = nextYear,
            )
        }
    }

    @Test
    fun `졸업예정 잘못된 날짜`() {
        assertThrows<GraduationInfoExceptions.InvalidGraduateDate> {
            checkGraduateDateService.checkIsInvalidYear(
                educationalStatus = EducationalStatus.PROSPECTIVE_GRADUATE,
                graduateDate = laterYears,
            )
        }
    }
}
