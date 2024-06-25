package hs.kr.equus.application.domain.application.usecase.dto.response

data class ApplicantCodeResponse (
    val receiptCode: Long,
    val examCode: String,
    val name: String
)