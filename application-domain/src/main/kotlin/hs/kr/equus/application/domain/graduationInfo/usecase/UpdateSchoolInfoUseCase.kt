package hs.kr.equus.application.domain.graduationInfo.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.spi.*
import hs.kr.equus.application.domain.graduationInfo.usecase.dto.request.UpdateSchoolInfoRequest
import hs.kr.equus.application.domain.school.exception.SchoolExceptions
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class UpdateSchoolInfoUseCase(
    private val securityPort: SecurityPort,
    private val graduationInfoQueryApplicationPort: GraduationInfoQueryApplicationPort,
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val commandGraduationInfoPort: CommandGraduationInfoPort,
    private val graduationInfoQuerySchoolPort: GraduationInfoQuerySchoolPort,
) {
    fun execute(request: UpdateSchoolInfoRequest) {
        val userId = securityPort.getCurrentUserId()

        val application =
            graduationInfoQueryApplicationPort.queryApplicationByUserId(userId)
                ?: throw ApplicationExceptions.ApplicationNotFoundException()
        
        val graduation: Graduation =
            application.educationalStatus?.let {
                queryGraduationInfoPort.queryGraduationInfoByReceiptCodeAndEducationalStatus(
                    application.receiptCode!!,
                    it,
                ) as Graduation
            } ?: throw GraduationInfoExceptions.EducationalStatusUnmatchedException()

        if (!graduationInfoQuerySchoolPort.isExistsSchoolBySchoolCode(request.schoolCode)) {
            throw SchoolExceptions.SchoolNotFoundException()
        }

        request.run {
            commandGraduationInfoPort.save(
                graduation.copy(
                    studentNumber = studentNumber,
                    schoolCode = schoolCode,
                ),
            )
        }
    }
}
