package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo

interface ApplicationQueryGraduationInfoPort {
    fun queryByReceiptCodeAndEducationalStatus(
        receiptCode: Long,
        educationalStatus: EducationalStatus,
    ): GraduationInfo?
}
