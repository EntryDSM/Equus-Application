package hs.kr.equus.application.domain.score.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import java.math.BigDecimal

data class GetStaticsScoreResponse(
    val isDaejeon: Boolean,
    val applicationType: ApplicationType,
    var firstRate: Int = 0,
    var secondRate: Int = 0,
    var thirdRate: Int = 0,
    var fourthRate: Int = 0,
    var fifthRate: Int = 0,
    var sixthRate: Int = 0,
    var seventhRate: Int = 0,
    var eighthRate: Int = 0,


)  {

    fun addScore(score: BigDecimal) {
        when (score) {
            in BigDecimal.ZERO..BigDecimal("79") -> eighthRate++
            in BigDecimal("80")..BigDecimal("92") -> seventhRate++
            in BigDecimal("93")..BigDecimal("105") -> sixthRate++
            in BigDecimal("106")..BigDecimal("118") -> fifthRate++
            in BigDecimal("119")..BigDecimal("131") -> fourthRate++
            in BigDecimal("132")..BigDecimal("144") -> thirdRate++
            in BigDecimal("145")..BigDecimal("157") -> secondRate++
            in BigDecimal("158")..BigDecimal("170") -> firstRate++
        }
    }
}
