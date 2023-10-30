package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.graduationInfo.model.Graduation

interface ApplicationQueryGraduationPort {
    fun queryGraduationByReceiptCode(receiptCode: Long): Graduation?
}
