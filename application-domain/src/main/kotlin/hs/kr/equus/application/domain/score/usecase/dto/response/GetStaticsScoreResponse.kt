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
    var ninthRate: Int = 0,
    var tenthRate: Int = 0,
)
