package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.service.GetAddScoreService
import hs.kr.equus.application.domain.score.spi.QueryScorePort
import hs.kr.equus.application.domain.score.usecase.dto.response.GetStaticsScoreResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QueryStaticsScoreUseCase(
    private val queryScorePort: QueryScorePort,
    private val getAddScoreService: GetAddScoreService
) {
    fun execute(): List<GetStaticsScoreResponse> {
        return ApplicationType.values().flatMap { type ->
            listOf(true, false).map { isDaejeon ->
                val response = GetStaticsScoreResponse(isDaejeon, type)
                queryScorePort.queryScoreByApplicationTypeAndIsDaejeon(type, isDaejeon).forEach {
                    it?.totalScore?.let { totalScore ->
                        val score = getAddScoreService.addScore(totalScore)
                        when (score) {
                            1 -> response.firstRate++
                            2 -> response.secondRate++
                            3 -> response.thirdRate++
                            4 -> response.fourthRate++
                            5 -> response.fifthRate++
                            6 -> response.sixthRate++
                            7 -> response.seventhRate++
                            8 -> response.eighthRate++
                        }
                    }
                }
                response
            }
        }
    }

}

