package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.service.ScoreCalculatorService
import hs.kr.equus.application.domain.score.spi.QueryScorePort
import hs.kr.equus.application.domain.score.usecase.dto.response.GetStaticsScoreResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QueryStaticsScoreUseCase(
    private val queryScorePort: QueryScorePort,
    private val scoreCalculatorService: ScoreCalculatorService
) {
    fun execute(): List<GetStaticsScoreResponse> {
        return ApplicationType.values().flatMap { type ->
            listOf(true, false).map { isDaejeon ->
                val totalScores = queryScorePort.queryScoreByApplicationTypeAndIsDaejeon(type, isDaejeon)
                    .map { it?.totalScore!! }
                    .let { scoreCalculatorService.ratingCalculator(it) }

                GetStaticsScoreResponse(
                    isDaejeon = isDaejeon,
                    applicationType = type,
                    firstRate = totalScores[0].toInt(),
                    secondRate = totalScores[1].toInt(),
                    thirdRate = totalScores[2].toInt(),
                    fourthRate = totalScores[3].toInt(),
                    fifthRate = totalScores[4].toInt(),
                    sixthRate = totalScores[5].toInt(),
                    seventhRate = totalScores[6].toInt(),
                    eighthRate = totalScores[7].toInt()
                )
            }
        }
    }
}

