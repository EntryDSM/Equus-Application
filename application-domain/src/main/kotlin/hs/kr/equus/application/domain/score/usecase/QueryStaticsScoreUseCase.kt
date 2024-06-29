package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.exception.ScoreExceptions
import hs.kr.equus.application.domain.score.spi.ScoreQueryApplicationTypeAndIsDaejeonPort
import hs.kr.equus.application.domain.score.usecase.dto.response.GetScoreResposne
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QueryStaticsScoreUseCase(
    private val scoreQueryApplicationTypeAndIsDaejeonPort: ScoreQueryApplicationTypeAndIsDaejeonPort
) {
    fun execute(): List<GetScoreResposne> {
        return ApplicationType.values().flatMap { type ->
            listOf(true, false).map { isDaejeon ->
                val response = when {
                    type == ApplicationType.COMMON -> GetScoreResposne(isDaejeon, ApplicationType.COMMON)
                    else -> GetScoreResposne(isDaejeon, type)
                }
                scoreQueryApplicationTypeAndIsDaejeonPort.queryScoreByApplicationTypeAndIsDaejeon(type, isDaejeon)
                    .forEach { score ->
                        response.addScore(score!!.totalScore!!.toDouble())
                    }
                response
            }
        }.toList()
    }
}