package hs.kr.equus.application.domain.application.event.dto

import java.time.YearMonth

data class UpdateEducationalStatusEvent(
    val receiptCode: Long,
    val graduateDate: YearMonth
)
