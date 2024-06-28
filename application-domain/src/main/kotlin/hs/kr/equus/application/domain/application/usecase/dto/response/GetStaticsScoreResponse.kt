package hs.kr.equus.application.domain.application.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.ApplicationType

abstract class GetStaticsScoreResponse(
    val applicationType: ApplicationType,
    val isDaejeon: Boolean = false
) {
    abstract fun addScore(score: Double)
}