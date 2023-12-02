package hs.kr.equus.application.domain.score.domain.mapper

import hs.kr.equus.application.domain.score.domain.entity.ScoreJpaEntity
import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.global.mapper.GenericMapper
import org.mapstruct.Mapper

@Mapper
abstract class ScoreMapper : GenericMapper<ScoreJpaEntity, Score> {
    abstract override fun toEntity(model: Score): ScoreJpaEntity

    abstract override fun toDomain(entity: ScoreJpaEntity?): Score?

    abstract override fun toDomainNotNull(entity: ScoreJpaEntity): Score
}
