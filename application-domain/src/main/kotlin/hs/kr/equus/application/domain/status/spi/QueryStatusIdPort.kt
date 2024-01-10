package hs.kr.equus.application.domain.status.spi

interface QueryStatusIdPort {
    fun getStatusByReceiptCode(receiptCode: Long)
}
