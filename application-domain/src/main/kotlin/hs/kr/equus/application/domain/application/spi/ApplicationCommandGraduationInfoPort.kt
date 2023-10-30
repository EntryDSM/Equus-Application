package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo

interface ApplicationCommandGraduationInfoPort {
    fun save(graduationInfo: GraduationInfo): GraduationInfo

    fun delete(graduationInfo: GraduationInfo)
}