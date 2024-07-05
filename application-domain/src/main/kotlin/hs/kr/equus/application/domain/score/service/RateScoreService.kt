package hs.kr.equus.application.domain.score.service

import hs.kr.equus.application.global.annotation.DomainService
import java.math.BigDecimal

@DomainService
class RateScoreService {
    fun rate(scores: List<BigDecimal>): Array<Int> {
        val ratingCounts = Array(8) { 0 }
        scores.map {
            score ->
            when {
                score < BigDecimal("80") -> ratingCounts[7]++
                score < BigDecimal("93") -> ratingCounts[6]++
                score < BigDecimal("106") -> ratingCounts[5]++
                score < BigDecimal("119") -> ratingCounts[4]++
                score < BigDecimal("132") -> ratingCounts[3]++
                score < BigDecimal("145") -> ratingCounts[2]++
                score < BigDecimal("158") -> ratingCounts[1]++
                score <= BigDecimal("170") -> ratingCounts[0]++
                else -> 0 // 범위 넘어가는 숫자는 0 반환
            }
        }
        return ratingCounts
    }
}
