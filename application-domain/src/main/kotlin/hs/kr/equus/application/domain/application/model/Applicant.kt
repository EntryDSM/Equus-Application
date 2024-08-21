package hs.kr.equus.application.domain.application.model

data class Applicant(
    val receiptCode: Long,
    val name: String?,
    val telephoneNumber: String?,
    val isDaejeon: Boolean?,
    val applicationType: String?,
    val isPrintsArrived: Boolean?,
    val isSubmitted: Boolean?,
    val isOutOfHeadcount: Boolean?
)
