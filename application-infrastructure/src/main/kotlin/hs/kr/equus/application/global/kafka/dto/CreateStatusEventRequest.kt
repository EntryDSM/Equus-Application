package hs.kr.equus.application.global.kafka.dto

import java.time.LocalDateTime

data class CreateStatusEventRequest(
    val isPrintsArrived: Boolean,
    val submittedAt: LocalDateTime?,
    val examCode: String?,
    val isFirstRoundPass: Boolean,
    val isSecondRoundPass: Boolean,
    val receiptCode: Long,
)
