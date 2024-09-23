package hs.kr.equus.application.domain.application.event.spi

import hs.kr.equus.application.domain.application.model.Application
import java.time.YearMonth

interface ApplicationEventPort {
    fun create(receiptCode: Long)

    fun updateEducationalStatus(application: Application, graduateDate: YearMonth)

    fun updateEducationalStatusRollback(application: Application, graduateDate: YearMonth)

    fun submitApplicationFinal(receiptCode: Long)

    fun createApplicationScoreRollback(receiptCode: Long)
}
