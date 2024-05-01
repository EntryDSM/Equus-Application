package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application

interface ApplicationQueryApplicationPort {
    fun queryApplicationByReceiptCode(receiptCode: Long): Application?
}