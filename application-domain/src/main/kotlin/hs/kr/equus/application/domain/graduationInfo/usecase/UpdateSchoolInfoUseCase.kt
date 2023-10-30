package hs.kr.equus.application.domain.graduationInfo.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.spi.*
import hs.kr.equus.application.domain.graduationInfo.usecase.dto.request.UpdateSchoolInfoRequest
import hs.kr.equus.application.domain.school.exception.SchoolExceptions
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class UpdateSchoolInfoUseCase(
    private val securityPort: SecurityPort,
    private val graduationInfoQueryApplicationPort: GraduationQueryApplicationPort,
    private val queryGraduationPort: QueryGraduationPort,
    private val commandGraduationInfoPort: CommandGraduationPort,
    private val graduationQuerySchoolPort: GraduationQuerySchoolPort,
) {
    fun execute(request: UpdateSchoolInfoRequest) {
        val userId = securityPort.getCurrentUserId()

        val receiptCode =
            graduationInfoQueryApplicationPort.queryReceiptCodeByUserId(userId)
                ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val graduation =
            queryGraduationPort.queryGraduationByReceiptCode(receiptCode)
                ?: throw GraduationInfoExceptions.EducationalStatusUnmatchedException()

        if (!graduationQuerySchoolPort.isExistsSchoolBySchoolCode(request.schoolCode)) {
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
