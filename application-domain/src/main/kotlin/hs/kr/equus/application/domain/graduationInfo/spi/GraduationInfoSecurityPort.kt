package hs.kr.equus.application.domain.graduationInfo.spi

import java.util.UUID

interface GraduationInfoSecurityPort {
    fun getCurrentUserId(): UUID
}
