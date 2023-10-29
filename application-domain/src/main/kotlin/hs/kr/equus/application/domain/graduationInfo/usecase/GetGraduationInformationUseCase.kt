package hs.kr.equus.application.domain.graduationInfo.usecase

import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationQueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationQuerySchoolPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationPort
import hs.kr.equus.application.domain.graduationInfo.usecase.dto.response.GetGraduationInformationResponse
import hs.kr.equus.application.domain.school.exception.SchoolExceptions
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.photo.spi.PhotoPort
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class GetGraduationInformationUseCase(
    private val securityPort: SecurityPort,
    private val queryGraduationInfoPort: QueryGraduationPort,
    private val graduationQueryApplicationPort: GraduationQueryApplicationPort,
    private val photoPort: PhotoPort,
    private val graduationQuerySchoolPort: GraduationQuerySchoolPort,
) {
    fun execute(): GetGraduationInformationResponse {

        val userId = securityPort.getCurrentUserId()

        val application = graduationQueryApplicationPort.queryApplicationByUserId(userId)

        val graduation = queryGraduationInfoPort.queryGraduationByReceiptCode(application.receiptCode!!)
            ?: throw GraduationInfoExceptions.EducationalStatusUnmatchedException()

        val school = graduation.schoolCode?.let { graduationQuerySchoolPort.querySchoolBySchoolCode(it) }
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
            schoolName = school.name
        )
    }
}
