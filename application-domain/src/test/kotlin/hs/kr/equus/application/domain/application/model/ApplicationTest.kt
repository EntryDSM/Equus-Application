package hs.kr.equus.application.domain.application.model

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.global.annotation.EquusTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.UUID

@EquusTest
class ApplicationTest {
    private val application =
        Application(
            receiptCode = 1L,
            userId = UUID.randomUUID(),
        )

    @Test
    fun `사회전형 입력 성공`() {
        assertDoesNotThrow {
            application.copy(
                applicationType = ApplicationType.SOCIAL,
                applicationRemark = ApplicationRemark.BASIC_LIVING,
            )
        }
    }

    @Test
    fun `사회전형으로 세부사항 미입력시 실패`() {
        assertThrows<ApplicationExceptions.InvalidApplicationRemarkException> {
            application.copy(
                applicationType = ApplicationType.SOCIAL,
                applicationRemark = null,
            )
        }
    }

    @Test
    fun `사회전형으로 제외되는 사항 입력시 실패`() {
        assertThrows<ApplicationExceptions.InvalidApplicationRemarkException> {
            application.copy(
                applicationType = ApplicationType.SOCIAL,
                applicationRemark = ApplicationRemark.PRIVILEGED_ADMISSION,
            )
        }
    }

    @Test
    fun `일반전형으로 세부사항 미입력시 성공`() {
        assertDoesNotThrow {
            application.copy(
                applicationType = ApplicationType.COMMON,
                applicationRemark = null,
            )
        }
    }

    @Test
    fun `일반전형으로 세부사항 입력시 성공`() {
        assertDoesNotThrow {
            application.copy(
                applicationType = ApplicationType.COMMON,
                applicationRemark = ApplicationRemark.PRIVILEGED_ADMISSION,
            )
        }
    }

    @Test
    fun `일반전형으로 사회전형용 사항 입력시 실패`() {
        assertThrows<ApplicationExceptions.InvalidApplicationRemarkException> {
            application.copy(
                applicationType = ApplicationType.COMMON,
                applicationRemark = ApplicationRemark.BASIC_LIVING,
            )
        }
    }
}
