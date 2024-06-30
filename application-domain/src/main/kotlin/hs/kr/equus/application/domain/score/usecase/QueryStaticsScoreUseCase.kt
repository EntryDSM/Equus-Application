package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.spi.ApplicationScorePort
import hs.kr.equus.application.domain.score.spi.ScoreQueryApplicationPort
import hs.kr.equus.application.domain.score.usecase.dto.response.GetStaticsScoreResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QueryStaticsScoreUseCase(
    private val applicationScorePort: ApplicationScorePort
) {
    fun execute(): List<GetStaticsScoreResponse> {
        return ApplicationType.values().flatMap { type ->
            listOf(true, false).map { isDaejeon ->
                val response = when {
                    type == ApplicationType.COMMON -> GetStaticsScoreResponse(isDaejeon, ApplicationType.COMMON)
                    else -> GetStaticsScoreResponse(isDaejeon, type)
                }
                applicationScorePort.queryScoreByApplicationTypeAndIsDaejeon(type, isDaejeon)
                    .forEach { score ->
                        response.addScore(score?.totalScore!!)
                    }
                response
            }
        }.toList()
    }
}
