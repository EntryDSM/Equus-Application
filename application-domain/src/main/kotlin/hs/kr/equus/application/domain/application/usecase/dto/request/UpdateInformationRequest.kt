package hs.kr.equus.application.domain.application.usecase.dto.request

import hs.kr.equus.application.domain.application.model.types.ParentRelation
import hs.kr.equus.application.domain.application.model.types.Sex
import java.time.LocalDate

data class UpdateInformationRequest(
    val sex: Sex,
    val birthDate: LocalDate,
    val applicantName: String,
    val applicantTel: String,
    val parentTel: String,
    val parentName: String,
    val parentRelation: String,
    val streetAddress: String,
    val postalCode: String,
    val detailAddress: String,
)

