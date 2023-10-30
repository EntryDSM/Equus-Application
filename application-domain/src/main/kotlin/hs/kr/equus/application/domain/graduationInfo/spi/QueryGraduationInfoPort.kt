package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo

interface QueryGraduationInfoPort {
    fun queryGraduationInfoByReceiptCodeAndEducationalStatus(
        receiptCode: Long,
        educationalStatus: EducationalStatus,
    ): GraduationInfo?
}
