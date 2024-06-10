package hs.kr.equus.application.domain.application.event.dto

import java.time.LocalDate

data class UpdateEducationStatusEvent(
    val receiptCode: Long,
    val graduateDate: LocalDate
)
