package hs.kr.equus.application.domain.score.spi

import hs.kr.equus.application.domain.score.model.Score

interface QueryScorePort {
    fun queryScoreByReceiptCode(receiptCode: Long): Score?
}