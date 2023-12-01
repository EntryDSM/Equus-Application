package hs.kr.equus.application.domain.score.domain

import hs.kr.equus.application.domain.score.domain.mapper.ScoreMapper
import hs.kr.equus.application.domain.score.domain.repository.ScoreJpaRepository
import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.domain.score.spi.ScorePort
import org.springframework.stereotype.Component

@Component
class ScorePersistenceAdapter(
    private val scoreMapper: ScoreMapper,
    private val scoreJpaRepository: ScoreJpaRepository,
) : ScorePort {
    override fun save(score: Score): Score {
        return scoreJpaRepository.save(
            scoreMapper.toEntity(score),
        ).let(scoreMapper::toDomainNotNull)
    }

    override fun queryScoreByReceiptCode(receiptCode: Long): Score? {
        return scoreJpaRepository.findByReceiptCode(receiptCode)
            .let(scoreMapper::toDomain)
    }
}
