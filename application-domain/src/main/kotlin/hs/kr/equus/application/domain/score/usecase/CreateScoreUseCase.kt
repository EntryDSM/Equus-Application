package hs.kr.equus.application.domain.score.usecase

import hs.kr.equus.application.domain.score.exception.ScoreExceptions
import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.domain.score.spi.CommandScorePort
import hs.kr.equus.application.domain.score.spi.QueryScorePort
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class CreateScoreUseCase(
    private val commandScorePort: CommandScorePort,
    private val queryScorePort: QueryScorePort,
) {
    fun execute(receiptCode: Long) {
        queryScorePort.queryScoreByReceiptCode(receiptCode)?.let {
            throw ScoreExceptions.ScoreExistsException()
        }

        commandScorePort.save(
            Score(receiptCode = receiptCode)
        )
    }
}
