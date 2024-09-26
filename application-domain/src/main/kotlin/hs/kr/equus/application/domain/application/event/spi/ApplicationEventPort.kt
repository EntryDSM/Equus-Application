package hs.kr.equus.application.domain.application.event.spi

import java.time.YearMonth
import java.util.UUID

interface ApplicationEventPort {
    fun create(receiptCode: Long, userId: UUID)

    fun updateEducationalStatus(receiptCode: Long, graduateDate: YearMonth)

    fun submitApplicationFinal(receiptCode: Long)

    fun createApplicationScoreRollback(receiptCode: Long)
}
