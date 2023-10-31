package hs.kr.equus.application.domain.status.model

import hs.kr.equus.application.global.annotation.Aggregate
import java.time.LocalDate
import java.time.LocalDateTime

@Aggregate
data class Status(
    val id: Long? = 0,
    val isPrintsArrived: Boolean = false,
    val submittedAt: LocalDateTime? = null,
    val examCode: String? = null,
    val isFirstRoundPass: Boolean = false,
    val isSecondRoundPass: Boolean = false,
    val receiptCode: Long,
)
