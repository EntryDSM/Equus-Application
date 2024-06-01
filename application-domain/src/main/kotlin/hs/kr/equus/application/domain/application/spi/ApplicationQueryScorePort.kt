package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.score.model.Score

interface ApplicationQueryScorePort {
    fun queryScoreByReceiptCode(receiptCode: Long): Score?
}