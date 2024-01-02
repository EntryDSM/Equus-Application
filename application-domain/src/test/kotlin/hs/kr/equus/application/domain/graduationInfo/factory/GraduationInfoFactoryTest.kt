package hs.kr.equus.application.domain.graduationInfo.factory

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.model.Qualification
import hs.kr.equus.application.global.annotation.EquusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

@EquusTest
class GraduationInfoFactoryTest {
    private val graduationInfoFactory: GraduationInfoFactory = GraduationInfoFactory()

    private val receiptCode: Long = 1L

    private val graduationDate = LocalDate.now()

    @Test
    fun `졸업전형 생성 성공`() {
        // given
        val graduation = Graduation(
            receiptCode = receiptCode,
            graduateDate = graduationDate,
            isProspectiveGraduate = false,
        )

        // when
        val graduationInfo = graduationInfoFactory.createGraduationInfo(
            receiptCode = receiptCode,
            educationalStatus = EducationalStatus.GRADUATE,
        )

        // then
        assertEquals(graduation, graduationInfo)
    }

    @Test
    fun `졸업예정전형 생성 성공`() {
        // given
        val prospectiveGraduation = Graduation(
            receiptCode = receiptCode,
            graduateDate = graduationDate,
            isProspectiveGraduate = true,
        )

        // when
        val graduationInfo = graduationInfoFactory.createGraduationInfo(
            receiptCode = receiptCode,
            educationalStatus = EducationalStatus.PROSPECTIVE_GRADUATE,
        )

        // then
        assertEquals(prospectiveGraduation, graduationInfo)
    }

    @Test
    fun `검정고시전형 생성 성공`() {
        // given
        val qualification = Qualification(
            receiptCode = receiptCode,
            isProspectiveGraduate = false,
        )

        // when
        val graduationInfo = graduationInfoFactory.createGraduationInfo(
            receiptCode = receiptCode,
            educationalStatus = EducationalStatus.QUALIFICATION_EXAM,
        )

        // then
        assertEquals(qualification, graduationInfo)
    }
}
