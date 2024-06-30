package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.ApplicationQueryStatusPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationStatusResponse
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.status.exception.StatusExceptions
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@ReadOnlyUseCase
class GetMyApplicationStatusUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: ApplicationCaseQueryApplicationPort,
    private val applicationQueryStatusPort: ApplicationQueryStatusPort
) {
    fun execute(): GetApplicationStatusResponse {
        val userId = securityPort.getCurrentUserId()

        val application = queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val status = applicationQueryStatusPort.queryStatusByReceiptCode(application.receiptCode)
            ?: throw StatusExceptions.StatusNotFoundException()

        return GetApplicationStatusResponse(
            receiptCode = application.receiptCode,
            phoneNumber = application.applicantTel!!,
            name = application.applicantName!!,
            isSubmitted = status.isSubmitted,
            isPrintedArrived = status.isPrintsArrived,
            selfIntroduce = application.selfIntroduce,
            studyPlan = application.studyPlan
        )
    }
}
