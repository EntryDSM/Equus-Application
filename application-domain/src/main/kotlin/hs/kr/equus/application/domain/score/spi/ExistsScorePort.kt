package hs.kr.equus.application.domain.score.spi

interface ExistsScorePort {
    fun existsById(receiptCode: Long): Boolean
}