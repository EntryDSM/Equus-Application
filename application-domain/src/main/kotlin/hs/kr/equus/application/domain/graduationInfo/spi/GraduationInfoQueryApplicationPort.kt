package hs.kr.equus.application.domain.graduationInfo.spi

import java.util.UUID

interface GraduationInfoQueryApplicationPort {
    fun queryReceiptCodeByUserId(userId: UUID): Long
}
