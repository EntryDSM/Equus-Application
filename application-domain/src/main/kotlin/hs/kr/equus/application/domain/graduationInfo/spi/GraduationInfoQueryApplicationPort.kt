package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.application.model.Application
import java.util.UUID

interface GraduationInfoQueryApplicationPort {
    fun queryReceiptCodeByUserId(userId: UUID): Long?

    fun queryApplicationByUserId(userId: UUID): Application?

    fun queryApplicationByReceiptCode(receiptCode: Long): Application?
}
