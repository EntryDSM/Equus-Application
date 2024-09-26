package hs.kr.equus.application.domain.application.event.dto

import java.util.UUID

data class CreateApplicationEvent(
    val receiptCode: Long,
    val userId: UUID
)
