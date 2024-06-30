package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.service.GetAddScoreService
import hs.kr.equus.application.domain.score.spi.ApplicationScorePort
import hs.kr.equus.application.domain.score.spi.ScoreQueryApplicationPort
import hs.kr.equus.application.domain.score.usecase.dto.response.GetStaticsScoreResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QueryStaticsScoreUseCase(
    private val applicationScorePort: ApplicationScorePort,
    private val getAddScoreService: GetAddScoreService
) {
    fun execute(): List<GetStaticsScoreResponse> {
        return ApplicationType.values().flatMap { type ->
            listOf(true, false).map { isDaejeon ->
                val response = when (type) {
                    ApplicationType.COMMON -> GetStaticsScoreResponse(isDaejeon, ApplicationType.COMMON)
                    else -> GetStaticsScoreResponse(isDaejeon, type)
                }

                val scores = applicationScorePort.queryScoreByApplicationTypeAndIsDaejeon(type, isDaejeon)
                scores.mapNotNull { it?.totalScore }.forEach { getAddScoreService.addScore(it) }

                response.apply {
                    firstRate = getAddScoreService.firstRate
                    secondRate = getAddScoreService.secondRate
                    thirdRate = getAddScoreService.thirdRate
                    fourthRate = getAddScoreService.fourthRate
                    fifthRate = getAddScoreService.fifthRate
                    sixthRate = getAddScoreService.sixthRate
                    seventhRate = getAddScoreService.seventhRate
                    eighthRate = getAddScoreService.eighthRate
                }.also { getAddScoreService.resetScore() }
            }
        }
    }
}
