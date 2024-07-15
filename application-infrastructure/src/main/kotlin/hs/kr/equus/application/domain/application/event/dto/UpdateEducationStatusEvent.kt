package hs.kr.equus.application.domain.application.event.dto

import java.time.YearMonth

data class UpdateEducationStatusEvent(
    val receiptCode: Long,
    val graduateDate: YearMonth
)
