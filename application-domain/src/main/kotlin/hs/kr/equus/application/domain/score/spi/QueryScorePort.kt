package hs.kr.equus.application.domain.score.spi

import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.domain.score.usecase.dto.response.QueryTotalScoreResponse
import java.math.BigDecimal

interface QueryScorePort {
    fun queryScoreByReceiptCode(receiptCode: Long): Score?

    fun queryTotalScore(receiptCode: Long): BigDecimal?
}
