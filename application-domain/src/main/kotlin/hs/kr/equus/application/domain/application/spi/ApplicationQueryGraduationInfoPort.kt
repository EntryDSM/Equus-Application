package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo

interface ApplicationQueryGraduationInfoPort {
    fun queryGraduationInfoByApplication(application: Application): GraduationInfo?

    fun queryAllGraduationByReceiptCode(receiptCodeList: List<Long>): List<GraduationInfo?>
}