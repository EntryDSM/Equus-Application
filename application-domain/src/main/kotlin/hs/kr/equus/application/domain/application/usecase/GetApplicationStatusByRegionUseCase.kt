package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.QueryApplicationInfoListByStatusIsSubmittedPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationStatusByRegionResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class GetApplicationStatusByRegionUseCase(
    private val queryApplicationInfoListByStatusIsSubmittedPort: QueryApplicationInfoListByStatusIsSubmittedPort
) {

    private val regionListMapping = mapOf(
        "서울" to { response: GetApplicationStatusByRegionResponse -> response.copy(seoul = response.seoul + 1) },
        "광주" to { response: GetApplicationStatusByRegionResponse -> response.copy(gwangju = response.gwangju + 1) },
        "대구" to { response: GetApplicationStatusByRegionResponse -> response.copy(daegu = response.daegu + 1) },
        "대전" to { response: GetApplicationStatusByRegionResponse -> response.copy(daejeon = response.daejeon + 1) },
        "부산" to { response: GetApplicationStatusByRegionResponse -> response.copy(busan = response.busan + 1) },
        "세종" to { response: GetApplicationStatusByRegionResponse -> response.copy(sejong = response.sejong + 1) },
        "울산" to { response: GetApplicationStatusByRegionResponse -> response.copy(ulsan = response.ulsan + 1) },
        "인천" to { response: GetApplicationStatusByRegionResponse -> response.copy(incheon = response.incheon + 1) },
        "제주" to { response: GetApplicationStatusByRegionResponse -> response.copy(jeju = response.jeju + 1) },
        "강원특별자치도" to { response: GetApplicationStatusByRegionResponse -> response.copy(gangwonDo = response.gangwonDo + 1) },
        "경기" to { response: GetApplicationStatusByRegionResponse -> response.copy(gyeonggiDo = response.gyeonggiDo + 1) },
        "경남" to { response: GetApplicationStatusByRegionResponse -> response.copy(gyeongsangnamDo = response.gyeongsangnamDo + 1) },
        "경북" to { response: GetApplicationStatusByRegionResponse -> response.copy(gyeongsangbukDo = response.gyeongsangbukDo + 1) },
        "전남" to { response: GetApplicationStatusByRegionResponse -> response.copy(jeollanamDo = response.jeollanamDo + 1) },
        "전북" to { response: GetApplicationStatusByRegionResponse -> response.copy(jeollabukDo = response.jeollabukDo + 1) },
        "충남" to { response: GetApplicationStatusByRegionResponse -> response.copy(chungcheongnamDo = response.chungcheongnamDo + 1) },
        "충북" to { response: GetApplicationStatusByRegionResponse -> response.copy(chungcheongbukDo = response.chungcheongbukDo + 1) }
    )

    fun execute(): GetApplicationStatusByRegionResponse {
        val applicationList =
            queryApplicationInfoListByStatusIsSubmittedPort.queryApplicationInfoListByStatusIsSubmitted(true)

        var response = GetApplicationStatusByRegionResponse()

        applicationList.forEach { application ->
            application.streetAddress?.let { address ->
                response = incrementRegionCount(response, address)
            }
        }

        return response
    }

    private fun incrementRegionCount(
        response: GetApplicationStatusByRegionResponse,
        address: String
    ): GetApplicationStatusByRegionResponse {
        var updatedResponse = response
        val applicantRegion = address.split(" ")[0]

        regionListMapping.forEach { (region, update) ->
            if (applicantRegion == region) {
                updatedResponse = update(updatedResponse)
            }
        }
        return updatedResponse
    }
}
