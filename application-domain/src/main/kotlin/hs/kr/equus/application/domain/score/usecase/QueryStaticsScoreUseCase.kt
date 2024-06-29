package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.spi.ScoreQueryApplicationTypeAndIsDaejeonPort
import hs.kr.equus.application.domain.score.usecase.dto.response.GetStaticsScoreResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QueryStaticsScoreUseCase(
    private val scoreQueryApplicationTypeAndIsDaejeonPort: ScoreQueryApplicationTypeAndIsDaejeonPort
) {
    fun execute(): List<GetStaticsScoreResponse> {
        val result = mutableListOf<GetStaticsScoreResponse>()

        for (type: ApplicationType in ApplicationType.values()) {
            for (isDaejeon in listOf(true, false)) {
                val response = when {
                    type.C-> CommonScoreResponse(isDaejeon)
                    else -> SpecialScoreResponse(type, isDaejeon)
                }
                scoreFacade.queryScoreByApplicationTypeAndIsDaejeon(type, isDaejeon)
                    .forEach { score ->
                        response.addScore(score.totalScore.toDouble().roundToInt())
                    }
                result.add(response)
            }
        }

        return result
    }
}