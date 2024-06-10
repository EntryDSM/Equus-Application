package hs.kr.equus.application.domain.graduationInfo.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.file.FilePathList
import hs.kr.equus.application.domain.file.spi.GenerateFileUrlPort
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQuerySchoolPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.domain.graduationInfo.usecase.dto.response.GetGraduationInformationResponse
import hs.kr.equus.application.domain.school.exception.SchoolExceptions
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class GetGraduationInformationUseCase(
    private val securityPort: SecurityPort,
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val graduationInfoQueryApplicationPort: GraduationInfoQueryApplicationPort,
    private val graduationInfoQuerySchoolPort: GraduationInfoQuerySchoolPort,
    private val generateFileUrlPort: GenerateFileUrlPort
) {
    fun execute(): GetGraduationInformationResponse {
        val userId = securityPort.getCurrentUserId()

        val application =
            graduationInfoQueryApplicationPort.queryApplicationByUserId(userId)
                ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val graduation =
            queryGraduationInfoPort.queryGraduationInfoByApplication(application)

        if(graduation !is Graduation) throw GraduationInfoExceptions.EducationalStatusUnmatchedException()

        val school =
            graduation.schoolCode?.let { graduationInfoQuerySchoolPort.querySchoolBySchoolCode(it) }
                ?: throw SchoolExceptions.SchoolNotFoundException()

        return GetGraduationInformationResponse(
            sex = application.sex,
            birthDate = application.birthDate,
            photoPath = generateFileUrlPort.generateFileUrl(FilePathList.APPLICATION+application.photoPath!!),
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
