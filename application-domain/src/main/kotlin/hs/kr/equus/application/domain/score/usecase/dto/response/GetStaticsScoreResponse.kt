package hs.kr.equus.application.domain.score.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.ApplicationType

abstract class GetStaticsScoreResponse(
    var applicationType: ApplicationType,
    var isDaejeon: Boolean = false
) {
    abstract fun addScore(score: Double)
}