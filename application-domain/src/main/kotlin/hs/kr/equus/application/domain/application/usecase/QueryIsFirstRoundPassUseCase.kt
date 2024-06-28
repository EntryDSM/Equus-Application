package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.ApplicationQueryStatusPort
import hs.kr.equus.application.domain.application.usecase.dto.response.QueryIsFirstRoundPassResponse
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.schedule.enums.Type
import hs.kr.equus.application.domain.schedule.spi.QueryScheduleTypePort
import hs.kr.equus.application.domain.status.exception.StatusExceptions
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import hs.kr.equus.application.global.security.spi.SecurityPort
import java.time.LocalDateTime

@ReadOnlyUseCase
class QueryIsFirstRoundPassUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: ApplicationCaseQueryApplicationPort,
    private val applicationQueryStatusPort: ApplicationQueryStatusPort,
    private val queryScheduleTypePort: QueryScheduleTypePort
) {
    fun execute(): QueryIsFirstRoundPassResponse {

        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val firstAnnounce = queryScheduleTypePort.queryScheduleType(Type.FIRST_ANNOUNCEMENT)
            ?: throw IllegalArgumentException("asdfasdf")
        if (LocalDateTime.now().isBefore(firstAnnounce.date)) throw Exception()

        val status = applicationQueryStatusPort.queryStatusByReceiptCode(application.receiptCode)
            ?: throw StatusExceptions.StatusNotFoundException()

        return QueryIsFirstRoundPassResponse(status.isFirstRoundPass)
    }
}