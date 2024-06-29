package hs.kr.equus.application.domain.score.presentation.dto.reponse

import com.fasterxml.jackson.annotation.JsonProperty
import hs.kr.equus.application.domain.application.model.types.ApplicationType

data class GetScoreResponse(
    val applicationType: ApplicationType,
    val isDaejeon: Boolean,
    val score158_170: Int,
    val score145_157: Int,
    val score132_144: Int,
    val score119_131: Int,
    val score106_118: Int,
    val score93_105: Int,
    val score80_92: Int,
    val score_79: Int
)
