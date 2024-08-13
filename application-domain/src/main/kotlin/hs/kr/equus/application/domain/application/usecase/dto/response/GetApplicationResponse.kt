package hs.kr.equus.application.domain.application.usecase.dto.response

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import java.math.BigDecimal
import java.time.LocalDate

data class GetApplicationResponse(
    val status: ApplicationStatusResponse,
    val commonInformation: ApplicationCommonInformationResponse,
    val moreInformation: ApplicationMoreInformationResponse?,
    val evaluation: ApplicationEvaluationResponse?
)

data class ApplicationCommonInformationResponse(
    val name: String,
    val schoolName: String?,
    val telephoneNumber: String,
    val schoolTel: String?,
    val parentTel: String?,
    val parentRelation: String?
)

data class ApplicationStatusResponse(
    val isPrintedArrived: Boolean,
    val isSubmit: Boolean
)

data class ApplicationMoreInformationResponse(
    val photoUrl: String,
    val birthDay: LocalDate,
    val educationalStatus: EducationalStatus,
    val applicationType: ApplicationType,
    val applicationRemark: ApplicationRemark?,
    val address: String,
    val detailAddress: String,
    val headCount: Any?
)

data class ApplicationEvaluationResponse(
    val volunteerTime: Int?,
    val conversionScore: BigDecimal,
    val dayAbsenceCount: Int?,
    val lectureAbsenceCount: Int?,
    val earlyLeaveCount: Int?,
    val latenessCount: Int?,
    val averageScore: BigDecimal?,
    val selfIntroduce: String,
    val studyPlan: String
)
