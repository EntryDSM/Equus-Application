package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo

interface QueryGraduationInfoPort {
    fun queryGraduationInfoByApplication(application: Application): GraduationInfo?
}
