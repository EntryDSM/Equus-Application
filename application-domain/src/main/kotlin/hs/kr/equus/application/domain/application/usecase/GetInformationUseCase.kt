package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetInformationResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@ReadOnlyUseCase
class GetInformationUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
) {
    fun execute(): GetInformationResponse {
        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        return application.run {
            GetInformationResponse(
                sex = sex,
                birthDate = birthDate,
                photoPath = photoPath,
                applicantName = applicantName,
                applicantTel = applicantTel,
                parentName = parentName,
                parentTel = parentTel,
                streetAddress = streetAddress,
                postalCode = postalCode,
                detailAddress = detailAddress,
            )
        }
    }
}
