package hs.kr.equus.application.domain.score.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import hs.kr.equus.application.domain.score.domain.mapper.ScoreMapper
import hs.kr.equus.application.domain.score.domain.repository.ScoreJpaRepository
import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.domain.score.spi.ScorePort
import hs.kr.equus.application.domain.score.usecase.dto.response.QueryTotalScoreResponse
import org.springframework.stereotype.Component
import hs.kr.equus.application.domain.score.domain.entity.QScoreJpaEntity.scoreJpaEntity

@Component
class ScorePersistenceAdapter(
    private val scoreMapper: ScoreMapper,
    private val scoreJpaRepository: ScoreJpaRepository,
    private val jpaQueryFactory: JPAQueryFactory
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

    override fun queryTotalScore(receiptCode: Long): QueryTotalScoreResponse {
        return jpaQueryFactory.select(scoreJpaEntity.totalScore)
            .from(scoreJpaEntity)
            .where(scoreJpaEntity.receiptCode.eq(receiptCode))
            .fetchOne()
            .let { QueryTotalScoreResponse(it) }
    }
}
