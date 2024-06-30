package hs.kr.equus.application.domain.score.service

import hs.kr.equus.application.global.annotation.DomainService
import java.math.BigDecimal

@DomainService
class GetAddScoreService {
    internal var firstRate: Int = 0
    internal var secondRate: Int = 0
    internal var thirdRate: Int = 0
    internal var fourthRate: Int = 0
    internal var fifthRate: Int = 0
    internal var sixthRate: Int = 0
    internal var seventhRate: Int = 0
    internal var eighthRate: Int = 0

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

    fun resetScore() {
        this.firstRate = 0
        this.secondRate = 0
        this.thirdRate = 0
        this.fourthRate = 0
        this.fifthRate = 0
        this.sixthRate = 0
        this.eighthRate = 0
    }
}
