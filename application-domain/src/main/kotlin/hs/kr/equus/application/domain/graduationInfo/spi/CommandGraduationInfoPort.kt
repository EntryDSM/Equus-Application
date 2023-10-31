package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo

interface CommandGraduationInfoPort {
    fun save(graduationInfo: GraduationInfo): GraduationInfo
}