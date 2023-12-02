package hs.kr.equus.application.domain.score.spi

import hs.kr.equus.application.domain.application.model.Application

interface ScoreQueryApplicationPort {
    fun queryApplicationByReceiptCode(receiptCode: Long): Application?
}