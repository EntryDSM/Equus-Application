package hs.kr.equus.application.domain.score.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.domain.entity.QScoreJpaEntity.scoreJpaEntity
import hs.kr.equus.application.domain.application.domain.entity.QApplicationJpaEntity.applicationJpaEntity
import hs.kr.equus.application.domain.score.domain.mapper.ScoreMapper
import hs.kr.equus.application.domain.score.domain.repository.ScoreJpaRepository
import hs.kr.equus.application.domain.score.exception.ScoreExceptions
import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.domain.score.spi.ScorePort
import hs.kr.equus.application.global.feign.client.StatusClient
import hs.kr.equus.application.global.feign.client.dto.response.StatusInfoElement
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ScorePersistenceAdapter(
    private val scoreMapper: ScoreMapper,
    private val scoreJpaRepository: ScoreJpaRepository,
    private val jpaQueryFactory: JPAQueryFactory,
    private val statusClient: StatusClient,
) : ScorePort {
    override fun save(score: Score): Score {
        return scoreJpaRepository.save(
            scoreMapper.toEntity(score),
        ).let(scoreMapper::toDomainNotNull)
    }

    override fun delete(score: Score) {
        scoreJpaRepository.delete(scoreMapper.toEntity(score))
    }

    override fun queryScoreByReceiptCode(receiptCode: Long): Score? {
        return scoreJpaRepository.findByReceiptCode(receiptCode)
            .let(scoreMapper::toDomain)
    }

    override fun queryScoreByApplicationTypeAndIsDaejeon(
        applicationType: ApplicationType, isDaejeon: Boolean
    ): List<Score?> {
        val statusMap: Map<Long, StatusInfoElement> =
            statusClient.getStatusList()
                .associateBy(StatusInfoElement::receiptCode)

        return jpaQueryFactory
            .select(scoreJpaEntity)
            .from(scoreJpaEntity)
            .where(
                scoreJpaEntity.receiptCode.`in`(
                    jpaQueryFactory
                        .select(applicationJpaEntity.receiptCode)
                        .from(applicationJpaEntity)
                        .where(
                            applicationJpaEntity.applicationType.eq(applicationType),
                            applicationJpaEntity.isDaejeon.eq(isDaejeon)
                        )
                        .fetch()
                )
            )
            .orderBy(scoreJpaEntity.totalScore.desc())
            .fetch()
            .filter { statusMap[it.receiptCode]?.isSubmitted == true }
            .map { scoreMapper.toDomain(it) }
    }
            
    override fun queryTotalScore(receiptCode: Long): BigDecimal? {

        return jpaQueryFactory.select(scoreJpaEntity.totalScore)
            .from(scoreJpaEntity)
            .where(scoreJpaEntity.receiptCode.eq(receiptCode))
            .fetchOne()
    }

    override fun queryAllByReceiptCode(receiptCodeList: List<Long>): List<Score?> {
        return scoreJpaRepository.findAllByReceiptCodeIn(receiptCodeList)
            .map { scoreMapper.toDomain(it) }
    }
}
