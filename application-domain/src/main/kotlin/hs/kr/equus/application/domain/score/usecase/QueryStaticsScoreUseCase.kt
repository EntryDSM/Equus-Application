package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.service.RateScoreService
import hs.kr.equus.application.domain.score.spi.QueryScorePort
import hs.kr.equus.application.domain.score.usecase.dto.response.GetStaticsScoreResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QueryStaticsScoreUseCase(
    private val queryScorePort: QueryScorePort,
    private val rateScoreService: RateScoreService
) {
    fun execute(): List<GetStaticsScoreResponse> {
        return ApplicationType.values().flatMap { type ->
            listOf(true, false).map { isDaejeon ->
                val totalScores = queryScorePort.queryScoreByApplicationTypeAndIsDaejeon(type, isDaejeon)
                    .map { it?.totalScore!! }
                    .let { rateScoreService.rate(it) }

                GetStaticsScoreResponse(
                    isDaejeon = isDaejeon,
                    applicationType = type,
                    firstRate = totalScores[0],
                    secondRate = totalScores[1],
                    thirdRate = totalScores[2],
                    fourthRate = totalScores[3],
                    fifthRate = totalScores[4],
                    sixthRate = totalScores[5],
                    seventhRate = totalScores[6],
                    eighthRate = totalScores[7],
                    ninthRate = totalScores[8],
                    tenthRate = totalScores[9],
                    eleventhRate = totalScores[10]
                )
            }
        }
    }
}
