package hs.kr.equus.application.domain.score.spi

import hs.kr.equus.application.domain.score.usecase.dto.response.QueryTotalScoreResponse

interface QueryTotalScorePort {
    fun queryTotalScore(receiptCode: Long): QueryTotalScoreResponse
}