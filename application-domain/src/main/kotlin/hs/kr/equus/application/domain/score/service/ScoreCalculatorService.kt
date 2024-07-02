package hs.kr.equus.application.domain.score.service

import hs.kr.equus.application.global.annotation.DomainService
import java.math.BigDecimal

@DomainService
class ScoreCalculatorService {
    fun ratingCalculator(scores: List<BigDecimal>): Array<BigDecimal> {
        val ratedScores = Array<BigDecimal>(8) { BigDecimal.ZERO }
        scores.map {
            score ->
            when {
                score < BigDecimal("80") -> ratedScores[7]++
                score < BigDecimal("93") -> ratedScores[6]++
                score < BigDecimal("106") -> ratedScores[5]++
                score < BigDecimal("119") -> ratedScores[4]++
                score < BigDecimal("132") -> ratedScores[3]++
                score < BigDecimal("145") -> ratedScores[2]++
                score < BigDecimal("158") -> ratedScores[1]++
                score <= BigDecimal("170") -> ratedScores[0]++
                else -> 0 // 범위 넘어가는 숫자는 0 반환
            }
        }
        return ratedScores
    }
}
