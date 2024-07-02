package hs.kr.equus.application.domain.score.service

import hs.kr.equus.application.domain.score.usecase.dto.response.GetStaticsScoreResponse
import hs.kr.equus.application.global.annotation.DomainService
import java.math.BigDecimal

@DomainService
class GetAddScoreService {
    fun addScore(score: BigDecimal): Int {
        return when {
            score < BigDecimal("80") -> 8
            score < BigDecimal("93") -> 7
            score < BigDecimal("106") -> 6
            score < BigDecimal("119") -> 5
            score < BigDecimal("132") -> 4
            score < BigDecimal("145") -> 3
            score < BigDecimal("158") -> 2
            score <= BigDecimal("170") -> 1
            else -> 0 // 범위 넘어가는 숫자는 0 반환
        }
    }


}
