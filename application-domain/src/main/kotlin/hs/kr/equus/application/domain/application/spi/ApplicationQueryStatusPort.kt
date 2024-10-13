package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.status.model.Status
import hs.kr.equus.application.domain.status.model.StatusCache

interface ApplicationQueryStatusPort {
    fun queryStatusByReceiptCode(receiptCode: Long): Status?
    fun queryStatusByReceiptCodeInCache(receiptCode: Long): StatusCache?
}