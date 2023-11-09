package hs.kr.equus.application.domain.application.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.Sex
import java.time.LocalDate

data class GetInformationResponse(
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
)
