package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.QueryApplicationInfoListByStatusIsSubmittedPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationStatusByRegionResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.RegionList
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class GetApplicationStatusByRegionUseCase(
    private val queryApplicationInfoListByStatusIsSubmittedPort: QueryApplicationInfoListByStatusIsSubmittedPort
) {

    private val regionListMapping = mapOf(
        "서울" to { list: RegionList -> list.copy(seoul = list.seoul + 1) },
        "광주" to { list: RegionList -> list.copy(gwangju = list.gwangju + 1) },
        "대구" to { list: RegionList -> list.copy(daegu = list.daegu + 1) },
        "대전" to { list: RegionList -> list.copy(daejeon = list.daejeon + 1) },
        "부산" to { list: RegionList -> list.copy(busan = list.busan + 1) },
        "세종" to { list: RegionList -> list.copy(sejong = list.sejong + 1) },
        "울산" to { list: RegionList -> list.copy(ulsan = list.ulsan + 1) },
        "인천" to { list: RegionList -> list.copy(incheon = list.incheon + 1) },
        "제주" to { list: RegionList -> list.copy(jeju = list.jeju + 1) },
        "강원특별자치도" to { list: RegionList -> list.copy(gangwonDo = list.gangwonDo + 1) },
        "경기도" to { list: RegionList -> list.copy(gyeonggiDo = list.gyeonggiDo + 1) },
        "경상남도" to { list: RegionList -> list.copy(gyeongsangnamDo = list.gyeongsangnamDo + 1) },
        "경상북도" to { list: RegionList -> list.copy(gyeongsangbukDo = list.gyeongsangbukDo + 1) },
        "전라남도" to { list: RegionList -> list.copy(jeollanamDo = list.jeollanamDo + 1) },
        "전라북도" to { list: RegionList -> list.copy(jeollabukDo = list.jeollabukDo + 1) },
        "충청남도" to { list: RegionList -> list.copy(chungcheongnamDo = list.chungcheongnamDo + 1) },
        "충청북도" to { list: RegionList -> list.copy(chungcheongbukDo = list.chungcheongbukDo + 1) }
    )

    fun execute(): GetApplicationStatusByRegionResponse {
        val applicationList = queryApplicationInfoListByStatusIsSubmittedPort.queryApplicationInfoListByStatusIsSubmitted(true)

        var regionCount = RegionList()

        applicationList.forEach { application ->
            application.streetAddress?.let { address ->
                regionCount = incrementProvinceCount(regionCount, address)
            }
        }

        return GetApplicationStatusByRegionResponse(
            regionList = listOf(regionCount),
        )
    }

    private fun incrementProvinceCount(regionList: RegionList, address: String): RegionList {
        regionListMapping.forEach { (region, update) ->
            if (address.contains(region)) {
                return update(regionList)
            }
        }
        return regionList
    }
}
