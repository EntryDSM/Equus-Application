package hs.kr.equus.application.domain.graduationInfo.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQuerySchoolPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.domain.graduationInfo.usecase.dto.response.GetGraduationInformationResponse
import hs.kr.equus.application.domain.school.exception.SchoolExceptions
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.photo.spi.PhotoPort
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class GetGraduationInformationUseCase(
    private val securityPort: SecurityPort,
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val graduationInfoQueryApplicationPort: GraduationInfoQueryApplicationPort,
    private val photoPort: PhotoPort,
    private val graduationInfoQuerySchoolPort: GraduationInfoQuerySchoolPort,
) {
    fun execute(): GetGraduationInformationResponse {
        val userId = securityPort.getCurrentUserId()

        val application =
            graduationInfoQueryApplicationPort.queryApplicationByUserId(userId)
                ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val graduation: Graduation =
            (
                    application.educationalStatus?.let {
                        queryGraduationInfoPort.queryGraduationInfoByReceiptCodeAndEducationalStatus(
                            application.receiptCode!!,
                            it,
                        )
                    } ?: throw GraduationInfoExceptions.EducationalStatusUnmatchedException()
                    ) as Graduation

        val school =
            graduation.schoolCode?.let { graduationInfoQuerySchoolPort.querySchoolBySchoolCode(it) }
                ?: throw SchoolExceptions.SchoolNotFoundException()

        return GetGraduationInformationResponse(
            sex = application.sex,
            birthDate = application.birthDate,
            photoUrl = application.photoFileName?.let { photoPort.getPhotoUrl(it) },
            applicantName = application.applicantName,
            applicantTel = application.applicantTel,
            parentTel = application.parentTel,
            parentName = application.parentName,
            streetAddress = application.streetAddress,
            postalCode = application.postalCode,
            detailAddress = application.detailAddress,
            studentNumber = graduation.studentNumber,
            schoolCode = school.code,
            schoolTel = school.tel,
            schoolName = school.name,
        )
    }
}
