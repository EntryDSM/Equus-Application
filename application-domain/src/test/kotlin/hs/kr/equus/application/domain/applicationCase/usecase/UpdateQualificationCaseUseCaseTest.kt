package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.event.spi.ApplicationCaseEventPort
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.UpdateQualificationCaseRequest
import hs.kr.equus.application.global.annotation.EquusTest
import hs.kr.equus.application.global.security.spi.SecurityPort
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.given
import org.springframework.boot.test.mock.mockito.MockBean
import java.math.BigDecimal
import java.util.UUID

@EquusTest
class UpdateQualificationCaseUseCaseTest {
    @MockBean
    private lateinit var securityPort: SecurityPort

    @MockBean
    private lateinit var applicationCaseQueryApplicationPort: ApplicationCaseQueryApplicationPort

    @MockBean
    private lateinit var commandApplicationCasePort: CommandApplicationCasePort

    @MockBean
    private lateinit var queryApplicationCasePort: QueryApplicationCasePort

    @MockBean
    private lateinit var qualificationCaseEventPort: ApplicationCaseEventPort

    private lateinit var updateQualificationCaseUseCase: UpdateQualificationCaseUseCase

    private val userId = UUID.randomUUID()

    private val receiptCode = 1L

    private val requestStub: UpdateQualificationCaseRequest by lazy {
        UpdateQualificationCaseRequest(
            averageScore = BigDecimal(70)
        )
    }

    private val applicationStub = Application(
        receiptCode = receiptCode,
        userId = userId,
    )

    private val qualificationCaseStub = QualificationCase(
        receiptCode = receiptCode,
    )

    @BeforeEach
    fun setUp() {
        updateQualificationCaseUseCase = UpdateQualificationCaseUseCase(
            securityPort,
            applicationCaseQueryApplicationPort,
            commandApplicationCasePort,
            queryApplicationCasePort,
            qualificationCaseEventPort,
        )
    }

    @Test
    fun `졸업전형 수정 성공`() {
        // given
        given(securityPort.getCurrentUserId())
            .willReturn(userId)

        given(applicationCaseQueryApplicationPort.queryApplicationByUserId(userId))
            .willReturn(applicationStub)

        given(queryApplicationCasePort.queryApplicationCaseByApplication(applicationStub))
            .willReturn(qualificationCaseStub)

        // when & then
        assertDoesNotThrow() {
            updateQualificationCaseUseCase.execute(requestStub)
        }
    }

    @Test
    fun `원서조회 실패`() {
        // given
        given(securityPort.getCurrentUserId())
            .willReturn(userId)

        given(applicationCaseQueryApplicationPort.queryApplicationByUserId(userId))
            .willReturn(null)

        given(queryApplicationCasePort.queryApplicationCaseByApplication(applicationStub))
            .willReturn(qualificationCaseStub)

        // when & then
        assertThrows<ApplicationExceptions.ApplicationNotFoundException> {
            updateQualificationCaseUseCase.execute(requestStub)
        }
    }

    @Test
    fun `졸업전형조회 실패`() {
        // given
        given(securityPort.getCurrentUserId())
            .willReturn(userId)

        given(applicationCaseQueryApplicationPort.queryApplicationByUserId(userId))
            .willReturn(applicationStub)

        given(queryApplicationCasePort.queryApplicationCaseByApplication(applicationStub))
            .willReturn(null)

        // when & then
        assertThrows<ApplicationCaseExceptions.EducationalStatusUnmatchedException> {
            updateQualificationCaseUseCase.execute(requestStub)
        }
    }
}