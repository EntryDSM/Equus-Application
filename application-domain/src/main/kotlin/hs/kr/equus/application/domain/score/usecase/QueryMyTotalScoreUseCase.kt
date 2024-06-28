package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.score.spi.QueryScorePort
import hs.kr.equus.application.domain.score.usecase.dto.response.QueryTotalScoreResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@ReadOnlyUseCase
class QueryMyTotalScoreUseCase(
    private val queryApplicationPort: QueryApplicationPort,
    private val securityPort: SecurityPort,
    private val queryScorePort: QueryScorePort
) {
    fun execute(): QueryTotalScoreResponse? {
        val userId = securityPort.getCurrentUserId()
        val applicationId = queryApplicationPort.queryApplicationByUserId(userId) ?: throw ApplicationExceptions.ApplicationNotFoundException()
        return queryScorePort.queryTotalScore(applicationId.receiptCode)
     }
}