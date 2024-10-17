package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.event.spi.ApplicationEventPort
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateEducationalStatusRequest
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.vo.ExtraScoreItem
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.spi.CommandGraduationInfoPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.global.annotation.EquusTest
import hs.kr.equus.application.global.security.spi.SecurityPort
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager
import java.time.YearMonth
import java.util.UUID

@EquusTest
class UpdateEducationalStatusUseCaseTest {
    private val securityPort = Mockito.mock(SecurityPort::class.java)
    private val queryApplicationPort = Mockito.mock(QueryApplicationPort::class.java)
    private val commandApplicationPort = Mockito.mock(CommandApplicationPort::class.java)
    private val applicationEventPort = Mockito.mock(ApplicationEventPort::class.java)
    private val commandApplicationCasePort = Mockito.mock(CommandApplicationCasePort::class.java)
    private val queryApplicationCasePort = Mockito.mock(QueryApplicationCasePort::class.java)
    private val queryGraduationInfoPort = Mockito.mock(QueryGraduationInfoPort::class.java)
    private val commandGraduationInfoPort = Mockito.mock(CommandGraduationInfoPort::class.java)

    private val userId = UUID.randomUUID()

    private val application = Application(
        receiptCode = 1L,
        userId = userId
    )

    private val request = UpdateEducationalStatusRequest(
        educationalStatus = EducationalStatus.PROSPECTIVE_GRADUATE,
        graduateDate = YearMonth.parse("2025-01")

    )

    private val useCase = UpdateEducationalStatusUseCase(
        securityPort, queryApplicationPort, commandApplicationPort,
        applicationEventPort, commandApplicationCasePort, queryApplicationCasePort,
        queryGraduationInfoPort, commandGraduationInfoPort
    )
    @Test
    @Rollback(false)
    fun `교육 상태 업데이트 요청 성공`() {
        given(securityPort.getCurrentUserId()).willReturn(userId)
        given(queryApplicationPort.queryApplicationByUserId(userId)).willReturn(application)
        useCase.execute(request)
        verify(commandApplicationPort).save(application.copy(educationalStatus = EducationalStatus.PROSPECTIVE_GRADUATE))
        verify(applicationEventPort).updateEducationalStatus(1L, YearMonth.parse("2025-01"))
    }




//    @Test
//    @Rollback(false)
//    fun `지원 케이스가 삭제되는 경우`() {
//        given(queryApplicationPort.queryApplicationByUserId(userId)).willReturn(application)
//        val applicationCase =
//            GraduationCase(
//                id = 0L,
//                extraScoreItem = ExtraScoreItem(false, false),
//                receiptCode = 1L,
//                volunteerTime = 1,
//                absenceDayCount = 1,
//                lectureAbsenceCount = 1,
//                latenessCount = 1,
//                earlyLeaveCount = 1,
//                koreanGrade = "AAAA",
//                socialGrade = "AAAA",
//                historyGrade = "AAAA",
//                mathGrade = "AAAA",
//                scienceGrade = "AAAA",
//                englishGrade = "AAAA",
//                techAndHomeGrade = "AAAA",
//                isProspectiveGraduate = false
//            )
//        val graduationInfo = Graduation(
//            id = 0L,
//            isProspectiveGraduate = false,
//            receiptCode = 1L
//        )
//        given(queryApplicationCasePort.queryApplicationCaseByApplication(application)).willReturn(applicationCase)
//        given(queryGraduationInfoPort.queryGraduationInfoByApplication(application)).willReturn(graduationInfo)
//
//        updateEducationalStatusUseCase.execute(request)
//        verify(commandApplicationCasePort).delete(applicationCase)
//        verify(commandGraduationInfoPort).delete(graduationInfo)
//    }
}
