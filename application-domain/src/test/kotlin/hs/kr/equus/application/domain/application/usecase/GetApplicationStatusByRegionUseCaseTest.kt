package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.spi.QueryApplicationInfoListByStatusIsSubmittedPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationStatusByRegionResponse
import hs.kr.equus.application.global.annotation.EquusTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

@EquusTest
class GetApplicationStatusByRegionUseCaseTest {

    private val queryApplicationInfoListByStatusIsSubmittedPort = Mockito.mock(QueryApplicationInfoListByStatusIsSubmittedPort::class.java)
    private val useCase = GetApplicationStatusByRegionUseCase(queryApplicationInfoListByStatusIsSubmittedPort)

    @Test
    fun `원서 최종제출 확인된 지역`() {
        val submittedApplications = listOf(
            Application(
                streetAddress = "서울 구청",
                userId = UUID.randomUUID()
            ),
            Application(
                streetAddress = "부산 해운대",
                userId = UUID.randomUUID()
            ),
            Application(
                streetAddress = "부산 부산진구",
                userId = UUID.randomUUID()
            ),
            Application(
                streetAddress = "서울 강남",
                userId = UUID.randomUUID()
            ),
            Application(
                streetAddress = "대구 동성로",
                userId = UUID.randomUUID()
            ),
            Application(
                streetAddress = "대구 동성로",
                userId = UUID.randomUUID()
            ),
            Application(
                streetAddress = "전북특별자치도 전주시",
                userId = UUID.randomUUID()
            ),
            Application(
                streetAddress = "강원특별자치도 양구군",
                userId = UUID.randomUUID()
            )

        )

        Mockito.`when`(queryApplicationInfoListByStatusIsSubmittedPort.queryApplicationInfoListByStatusIsSubmitted(true))
            .thenReturn(submittedApplications)

        val testResponse = useCase.execute()

        val response = GetApplicationStatusByRegionResponse(
            seoul = 2,
            busan = 2,
            daegu = 2,
            gwangju = 0,
            daejeon = 0,
            ulsan = 0,
            incheon = 0,
            jeju = 0,
            gangwonDo = 1,
            gyeonggiDo = 0,
            gyeongsangnamDo = 0,
            gyeongsangbukDo = 0,
            jeollanamDo = 0,
            jeollabukDo = 1,
            chungcheongnamDo = 0,
            chungcheongbukDo = 0,
            sejong = 0
        )

        assertEquals(response, testResponse)
    }
}
