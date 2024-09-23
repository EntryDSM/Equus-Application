package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.score.exception.ScoreExceptions
import hs.kr.equus.application.domain.score.spi.CommandScorePort
import hs.kr.equus.application.domain.score.spi.QueryScorePort
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class DeleteScoreUseCase(
    private val queryScorePort: QueryScorePort,
    private val commendScorePort: CommandScorePort
) {
    fun execute(receiptCode: Long) {
        val score = queryScorePort.queryScoreByReceiptCode(receiptCode)
            ?: throw ScoreExceptions.ScoreNotFoundException()
        commendScorePort.delete(score)
    }
}
