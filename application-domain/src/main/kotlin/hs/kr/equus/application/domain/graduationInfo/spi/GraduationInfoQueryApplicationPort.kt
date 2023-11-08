package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.application.model.Application
import java.util.*

interface GraduationInfoQueryApplicationPort {
    fun queryReceiptCodeByUserId(userId: UUID): Long?

    fun queryApplicationByUserId(userId: UUID): Application?
}
