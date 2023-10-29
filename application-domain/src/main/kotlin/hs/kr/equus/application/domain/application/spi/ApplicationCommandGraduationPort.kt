package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.graduationInfo.model.Graduation

interface ApplicationCommandGraduationPort {
    fun save(graduation: Graduation): Graduation
    fun delete(graduation: Graduation)
}
