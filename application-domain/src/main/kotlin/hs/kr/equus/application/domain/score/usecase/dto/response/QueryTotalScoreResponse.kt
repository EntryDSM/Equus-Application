package hs.kr.equus.application.domain.score.usecase.dto.response

import java.math.BigDecimal

data class QueryTotalScoreResponse (
    val totalScore: BigDecimal?
)