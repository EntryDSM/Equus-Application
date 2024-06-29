package hs.kr.equus.application.domain.score.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.ApplicationType

data class GetStaticsScoreResponse(
    val isDaejeon: Boolean,
    val applicationType: ApplicationType,
    var score158_170: Int = 0,
    var score145_157: Int = 0,
    var score132_144: Int = 0,
    var score119_131: Int = 0,
    var score106_118: Int = 0,
    var score93_105: Int = 0,
    var score80_92: Int = 0,
    var score_79: Int = 0

)  {

     fun addScore(score: Double) {
        when {
            score <= 79 -> score_79++
            score <= 92 -> score80_92++
            score <= 105 -> score93_105++
            score <= 118 -> score106_118++
            score <= 131 -> score119_131++
            score <= 144 -> score132_144++
            score <= 157 -> score145_157++
            score <= 170 -> score158_170++
        }
    }
}
