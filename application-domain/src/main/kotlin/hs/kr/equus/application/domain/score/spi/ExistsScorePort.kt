package hs.kr.equus.application.domain.score.spi

interface ExistsScorePort {
    fun existsByReceiptCode(receiptCode: Long): Boolean
}