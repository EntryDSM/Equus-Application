package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.global.annotation.EquusTest
import hs.kr.equus.application.global.security.spi.SecurityPort
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.boot.test.mock.mockito.MockBean
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

import java.math.BigDecimal
import java.util.UUID

@EquusTest
class GetQualificationCaseUseCaseTest {
    @MockBean
    private lateinit var securityPort: SecurityPort

    @MockBean
    private lateinit var applicationCaseQueryApplicationPort: ApplicationCaseQueryApplicationPort

    @MockBean
    private lateinit var queryApplicationCasePort: QueryApplicationCasePort

    private lateinit var getQualificationCaseUseCase: GetQualificationCaseUseCase

    private val userId = UUID.randomUUID()

    private val receiptCode = 1L

    private val applicationStub = Application(
        receiptCode = receiptCode,
        userId = userId,
    )

    private val qualificationCaseStub = QualificationCase(
        receiptCode = receiptCode,
        averageScore = BigDecimal(90)
    )

    @BeforeEach
    fun setUp() {
        getQualificationCaseUseCase = GetQualificationCaseUseCase(
            securityPort,
            applicationCaseQueryApplicationPort,
            queryApplicationCasePort,
        )
    }

    @Test
    fun `검정고시 성적 조회 성공`() {
        //given
        given(securityPort.getCurrentUserId())
            .willReturn(userId)

        given(applicationCaseQueryApplicationPort.queryApplicationByUserId(any()))
            .willReturn(applicationStub)

        given(queryApplicationCasePort.queryApplicationCaseByApplication(any()))
            .willReturn(qualificationCaseStub)

        //when & then
        assertEquals(BigDecimal(90), getQualificationCaseUseCase.execute().averageScore)
    }

    @Test
    fun `원서 없을시 실패`() {
        //given
        given(securityPort.getCurrentUserId())
            .willReturn(userId)

        given(applicationCaseQueryApplicationPort.queryApplicationByUserId(any()))
            .willReturn(null)

        given(queryApplicationCasePort.queryApplicationCaseByApplication(any()))
            .willReturn(qualificationCaseStub)

        //when & then
        assertThrows<ApplicationExceptions.ApplicationNotFoundException> { getQualificationCaseUseCase.execute() }
    }

    @Test
    fun `검정고시 전형 아닐시 실패`() {
        //given
        val anotherCase: ApplicationCase = GraduationCase(
            isProspectiveGraduate = false,
            receiptCode = 1L
        )

        given(securityPort.getCurrentUserId())
            .willReturn(userId)

        given(applicationCaseQueryApplicationPort.queryApplicationByUserId(any()))
            .willReturn(applicationStub)

        given(queryApplicationCasePort.queryApplicationCaseByApplication(any()))
            .willReturn(anotherCase)

        //when & then
        assertThrows<ApplicationCaseExceptions.EducationalStatusUnmatchedException> { getQualificationCaseUseCase.execute() }
    }

    @Test
    fun `전형이 null일시 실패`() {
        //given
        given(securityPort.getCurrentUserId())
            .willReturn(userId)

        given(applicationCaseQueryApplicationPort.queryApplicationByUserId(any()))
            .willReturn(applicationStub)

        given(queryApplicationCasePort.queryApplicationCaseByApplication(any()))
            .willReturn(null)

        //when & then
        assertThrows<ApplicationCaseExceptions.EducationalStatusUnmatchedException> { getQualificationCaseUseCase.execute() }
    }
}