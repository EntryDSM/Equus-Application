package hs.kr.equus.application.domain.graduationInfo.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.Sex
import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import java.time.LocalDate

data class GetGraduationInformationResponse(
    val sex: Sex?,
    val birthDate: LocalDate?,
    val photoPath: String?,
    val applicantName: String?,
    val applicantTel: String?,
    val parentTel: String?,
    val parentName: String?,
    val streetAddress: String?,
    val postalCode: String?,
    val detailAddress: String?,
    val schoolCode: String?,
    val schoolName: String?,
    val schoolTel: String?,
    val studentNumber: StudentNumber?,
)
