package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo

interface QueryGraduationInfoPort {
    fun queryGraduationInfoByReceiptCode(receiptCode: Long): GraduationInfo

    fun isExistsByReceiptCode(receiptCode: Long): Boolean
}
