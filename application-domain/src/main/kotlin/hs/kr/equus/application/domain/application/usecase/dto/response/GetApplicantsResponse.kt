package hs.kr.equus.application.domain.application.usecase.dto.response

data class GetApplicantsResponse(
    val applicants: List<ApplicantDto>,
    val hasNextPage: Boolean
)

data class ApplicantDto(
    val receiptCode: Long,
    val name: String?,
    val telephoneNumber: String?,
    val isDaejeon: Boolean?,
    val applicationType: String?,
    val isPrintsArrived: Boolean?,
    val isSubmitted: Boolean?,
    val isOutOfHeadcount: Boolean?
)