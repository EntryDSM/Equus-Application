package hs.kr.equus.application.domain.score.service

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.global.annotation.DomainService
import java.math.BigDecimal

@DomainService
class RateScoreService {
    fun rate(scores: List<BigDecimal>, applicationType: ApplicationType): Array<Int> {
        val ratingCounts = when(applicationType) {
            ApplicationType.COMMON -> Array(8) { 0 }
            else -> Array(7) { 0 }
        }

        scores.map { score ->
            if (applicationType == ApplicationType.COMMON) {
                when {
                    score < BigDecimal(72) -> ratingCounts[7]++
                    score < BigDecimal(86) -> ratingCounts[6]++
                    score < BigDecimal(100) -> ratingCounts[5]++
                    score < BigDecimal(114) -> ratingCounts[4]++
                    score < BigDecimal(128) -> ratingCounts[3]++
                    score < BigDecimal(142) -> ratingCounts[2]++
                    score < BigDecimal(156) -> ratingCounts[1]++
                    score < BigDecimal(174) -> ratingCounts[0]++
                    else -> 0
                }
            } else {
                when {
                    score < BigDecimal(54) -> ratingCounts[6]++
                    score < BigDecimal(62) -> ratingCounts[5]++
                    score < BigDecimal(78) -> ratingCounts[4]++
                    score < BigDecimal(86) -> ratingCounts[3]++
                    score < BigDecimal(94) -> ratingCounts[2]++
                    score < BigDecimal(102) -> ratingCounts[1]++
                    score < BigDecimal(120) -> ratingCounts[0]++
                    else -> 0 // 범위 넘어가는 숫자는 0 반환
                }
            }
        }
        return ratingCounts
    }
}
