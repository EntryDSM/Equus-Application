package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.status.model.Status

interface ApplicationQueryStatusPort {
    fun queryStatusByReceiptCode(receiptCode: Long): Status?
}