package hs.kr.equus.application.domain.graduationInfo.usecase.dto.request

data class UpdateGraduationInformationRequest(
    val gradeNumber: String,
    val classNumber: String,
    val studentNumber: String,
    val schoolCode: String,
)
