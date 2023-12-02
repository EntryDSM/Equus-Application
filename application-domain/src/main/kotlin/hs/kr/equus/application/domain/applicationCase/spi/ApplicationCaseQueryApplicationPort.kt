package hs.kr.equus.application.domain.applicationCase.spi

import hs.kr.equus.application.domain.application.model.Application
import java.util.UUID

interface ApplicationCaseQueryApplicationPort {
    fun queryApplicationByUserId(userId: UUID): Application?

    fun queryApplicationByReceiptCode(receiptCode: Long): Application?
}