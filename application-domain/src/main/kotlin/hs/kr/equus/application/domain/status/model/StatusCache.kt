package hs.kr.equus.application.domain.status.model

data class StatusCache(
    val receiptCode: Long,
    val isPrintsArrived: Boolean,
    val isSubmitted: Boolean,
    val examCode: String?,
    val isFirstRoundPass: Boolean,
    val isSecondRoundPass: Boolean,
    val ttl: Long
)