package hs.kr.equus.application.domain.score.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.ApplicationType

data class SpecialScoreResponse(
    override val applicationType: ApplicationType,
    override val isDaejeon: Boolean
) : GetStaticsScoreResponse(applicationType, isDaejeon) {
    var score98_110: Int = 0
    var score85_97: Int = 0
    var score72_84: Int = 0
    var score59_71: Int = 0
    var score46_58: Int = 0
    var score33_45: Int = 0
    var score20_32: Int = 0
    var score_19: Int = 0

    override fun addScore(score: Double) {
        when {
            score <= 19 -> score_19++
            score <= 32 -> score20_32++
            score <= 45 -> score33_45++
            score <= 58 -> score46_58++
            score <= 71 -> score59_71++
            score <= 84 -> score72_84++
            score <= 97 -> score85_97++
            score <= 110 -> score98_110++
        }
    }
}
