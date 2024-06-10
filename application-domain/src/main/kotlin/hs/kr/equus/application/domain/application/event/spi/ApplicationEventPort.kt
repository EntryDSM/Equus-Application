package hs.kr.equus.application.domain.application.event.spi

import java.time.LocalDate

interface ApplicationEventPort {
    fun create(receiptCode: Long)

    fun updateEducationalStatus(receiptCode: Long, graduateDate: LocalDate)

    fun submitApplicationFinal(receiptCode: Long)
}
