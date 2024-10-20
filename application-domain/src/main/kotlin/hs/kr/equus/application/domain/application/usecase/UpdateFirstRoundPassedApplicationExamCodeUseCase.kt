package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.service.LocationService
import hs.kr.equus.application.domain.application.spi.ApplicationCommandStatusPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryLatitudeAndLongitudePort
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class UpdateFirstRoundPassedApplicationExamCodeUseCase(
    private val queryApplicationPort: QueryApplicationPort,
    private val queryLatitudeAndLongitudePort: QueryLatitudeAndLongitudePort,
    private val locationService: LocationService,
    private val applicationCommandStatusPort: ApplicationCommandStatusPort
) {
    fun execute() {
        val firstRoundPassedApplications =
            queryApplicationPort.queryAllFirstRoundPassedApplication()

        val locationInfoMap: MutableMap<Long, Pair<Double, Double>> = mutableMapOf()
        val nonDaejeonApplications = mutableListOf<Pair<Application, Double>>()

        firstRoundPassedApplications.forEach {
            if (!it.isDaejeon!!) {
                val latitudeAndLongitude =
                    queryLatitudeAndLongitudePort.queryLatitudeAndLongitudeByStreetAddress(it.streetAddress!!)
                locationInfoMap[it.receiptCode] = latitudeAndLongitude

                val distance = locationService.calculateDistanceFromDSM(
                    latitudeAndLongitude.first, latitudeAndLongitude.second
                )
                nonDaejeonApplications.add(it to distance)
            }
        }

        nonDaejeonApplications.sortBy { it.second }

        var daejeonCounter = 1
        var nonDaejeonCounter = 1

        firstRoundPassedApplications.forEach {
            val firstDigit = when (it.applicationType!!) {
                ApplicationType.COMMON -> 1
                ApplicationType.MEISTER -> 2
                ApplicationType.SOCIAL -> 3
            }

            val secondDigit = if (it.isDaejeon!!) 1 else 2

            val thirdDigit = if (it.isDaejeon!!) {
                daejeonCounter++
            } else {
                nonDaejeonCounter++
            }

            val examCode = "$firstDigit$secondDigit${thirdDigit.toString().padStart(3, '0')}"
            applicationCommandStatusPort.updateExamCode(it.receiptCode!!, examCode)
        }
    }
}
