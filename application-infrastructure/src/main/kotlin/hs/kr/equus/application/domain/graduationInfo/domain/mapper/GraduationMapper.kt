package hs.kr.equus.application.domain.graduationInfo.domain.mapper

import hs.kr.equus.application.domain.graduationInfo.domain.entity.GraduationJpaEntity
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.global.mapper.GenericMapper
import org.mapstruct.Mapper

@Mapper
abstract class GraduationMapper : GenericMapper<GraduationJpaEntity, Graduation> {
    abstract override fun toEntity(model: Graduation): GraduationJpaEntity

    abstract override fun toDomain(entity: GraduationJpaEntity?): Graduation?

    abstract override fun toDomainNotNull(entity: GraduationJpaEntity): Graduation
}