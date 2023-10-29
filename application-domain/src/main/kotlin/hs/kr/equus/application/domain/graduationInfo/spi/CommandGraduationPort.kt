package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.graduationInfo.model.Graduation

interface CommandGraduationPort {
    fun save(graduation: Graduation): Graduation
}
