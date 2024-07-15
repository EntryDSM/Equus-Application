package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.event.spi.ApplicationCaseEventPort
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.vo.ExtraScoreItem
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.ExtraScoreRequest
import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.UpdateGraduationCaseRequest
import hs.kr.equus.application.global.annotation.EquusTest
import hs.kr.equus.application.global.security.spi.SecurityPort
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.given
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@EquusTest
class UpdateGraduationCaseUseCaseTest {
    @MockBean
    private lateinit var securityPort: SecurityPort

    @MockBean
    private lateinit var applicationCaseQueryApplicationPort: ApplicationCaseQueryApplicationPort

    @MockBean
    private lateinit var commandApplicationCasePort: CommandApplicationCasePort

    @MockBean
    private lateinit var queryApplicationCasePort: QueryApplicationCasePort

    @MockBean
    private lateinit var graduationCaseEventPort: ApplicationCaseEventPort

    private lateinit var updateGraduationCaseUseCase: UpdateGraduationCaseUseCase

    private val userId = UUID.randomUUID()

    private val receiptCode = 1L

    private val requestStub: UpdateGraduationCaseRequest by lazy {
        val extraScore = ExtraScoreRequest(hasCertificate = true, hasCompetitionPrize = true)
        UpdateGraduationCaseRequest(
            volunteerTime = 1,
            absenceDayCount = 1,
            lectureAbsenceCount = 1,
            latenessCount = 1,
            earlyLeaveCount = 1,
            koreanGrade = "AAAA",
            socialGrade = "AAAA",
            historyGrade = "AAAA",
            mathGrade = "AAAA",
            scienceGrade = "AAAA",
            englishGrade = "AAAA",
            techAndHomeGrade = "AAAA",
            extraScore = extraScore
        )
    }

    private val applicationStub = Application(
        receiptCode = receiptCode,
        userId = userId,
    )

    private val graduationCaseStub = GraduationCase(
        receiptCode = receiptCode,
        isProspectiveGraduate = true,
        extraScoreItem = ExtraScoreItem(hasCertificate = true, hasCompetitionPrize = true)
    )

    @BeforeEach
    fun setUp() {
        updateGraduationCaseUseCase = UpdateGraduationCaseUseCase(
            securityPort,
            applicationCaseQueryApplicationPort,
            commandApplicationCasePort,
            queryApplicationCasePort,
            graduationCaseEventPort,
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
            .willReturn(graduationCaseStub)

        // when & then
        assertDoesNotThrow() {
            updateGraduationCaseUseCase.execute(requestStub)
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
            .willReturn(graduationCaseStub)

        // when & then
        assertThrows<ApplicationExceptions.ApplicationNotFoundException> {
            updateGraduationCaseUseCase.execute(requestStub)
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
            updateGraduationCaseUseCase.execute(requestStub)
        }
    }
}
