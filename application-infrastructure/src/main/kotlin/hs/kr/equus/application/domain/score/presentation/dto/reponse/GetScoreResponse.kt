package hs.kr.equus.application.domain.score.presentation.dto.reponse

import com.fasterxml.jackson.annotation.JsonProperty
import hs.kr.equus.application.domain.application.model.types.ApplicationType

data class GetScoreResponse(
    val applicationType: ApplicationType,
    val isDaejeon: Boolean,
    @JsonProperty("158-170") val score158_170: Int = 0,
    @JsonProperty("145-157") val score145_157: Int = 0,
    @JsonProperty("132-144") val score132_144: Int = 0,
    @JsonProperty("119-131") val score119_131: Int = 0,
    @JsonProperty("106-118") val score106_118: Int = 0,
    @JsonProperty("93-105") val score93_105: Int = 0,
    @JsonProperty("80-92") val score80_92: Int = 0,
    @JsonProperty("-79") val score_79: Int = 0
)
