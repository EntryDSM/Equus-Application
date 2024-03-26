package hs.kr.equus.application.global.feign.client.dto.response

data class StatusInfoElement(
    val id: Long,
    var isPrintsArrived: Boolean = false,
    var isSubmitted: Boolean = false,
    var examCode: String? = null,
    var isFirstRoundPass: Boolean = false,
    var isSecondRoundPass: Boolean = false,
    val receiptCode: Long,
)
