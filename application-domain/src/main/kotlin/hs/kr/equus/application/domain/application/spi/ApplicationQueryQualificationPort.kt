package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.graduationInfo.model.Qualification

interface ApplicationQueryQualificationPort {
    fun queryQualificationByReceiptCode(receiptCode: Long): Qualification?
}
