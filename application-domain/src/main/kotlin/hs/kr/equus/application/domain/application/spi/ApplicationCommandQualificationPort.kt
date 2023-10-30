package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.graduationInfo.model.Qualification

interface ApplicationCommandQualificationPort {
    fun save(qualification: Qualification): Qualification

    fun delete(qualification: Qualification)
}
