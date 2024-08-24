package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.service.RateScoreService
import hs.kr.equus.application.domain.score.spi.QueryScorePort
import hs.kr.equus.application.domain.score.usecase.dto.response.GetStaticsScoreResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import java.math.BigDecimal

@ReadOnlyUseCase
class QueryStaticsScoreUseCase(
    private val queryScorePort: QueryScorePort,
    private val rateScoreService: RateScoreService
) {
    fun execute(): List<GetStaticsScoreResponse> {
        return ApplicationType.values().flatMap { type ->
            listOf(true, false).map { isDaejeon ->
                val totalScores = queryScorePort.queryScoreByApplicationTypeAndIsDaejeon(type, isDaejeon)
                    .map { it?.totalScore ?: BigDecimal.ZERO }
                    .let { rateScoreService.rate(it, type) }

                GetStaticsScoreResponse(
                    isDaejeon = isDaejeon,
                    applicationType = type,
                    totalScore = totalScores.toList()
                )
            }
        }
    }
}
