package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.graduationInfo.model.Graduation

interface QueryGraduationPort {
    fun queryGraduationByReceiptCode(receiptCode: Long): Graduation?
}
