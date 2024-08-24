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
                score < BigDecimal(81) -> ratingCounts[10]++
                score < BigDecimal(91) -> ratingCounts[9]++
                score < BigDecimal(101) -> ratingCounts[8]++
                score < BigDecimal(111) -> ratingCounts[7]++
                score < BigDecimal(121) -> ratingCounts[6]++
                score < BigDecimal(131) -> ratingCounts[5]++
                score < BigDecimal(141) -> ratingCounts[4]++
                score < BigDecimal(151) -> ratingCounts[3]
                score < BigDecimal(161) -> ratingCounts[2]
                score < BigDecimal(171) -> ratingCounts[1]
                score <= BigDecimal(170) -> ratingCounts[0]++
                else -> 0 // 범위 넘어가는 숫자는 0 반환
            }
        }
        return ratingCounts
    }
}
