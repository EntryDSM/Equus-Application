package hs.kr.equus.application.domain.application.usecase.dto.response

data class GetApplicationStatusResponse(
    val receiptCode: Long,
    val phoneNumber: String?,
    val name: String?,
    val isSubmitted: Boolean,
    val isPrintedArrived: Boolean,
    val selfIntroduce: String?,
    val studyPlan: String?
)
