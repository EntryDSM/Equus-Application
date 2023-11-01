package hs.kr.equus.application.domain.graduationInfo.domain.mapper

import hs.kr.equus.application.domain.graduationInfo.domain.entity.QualificationJpaEntity
import hs.kr.equus.application.domain.graduationInfo.model.Qualification
import hs.kr.equus.application.global.mapper.GenericMapper
import org.mapstruct.Mapper

@Mapper
abstract class QualificationMapper : GenericMapper<QualificationJpaEntity, Qualification> {
    abstract override fun toEntity(model: Qualification): QualificationJpaEntity

    abstract override fun toDomain(entity: QualificationJpaEntity?): Qualification?

    abstract override fun toDomainNotNull(entity: QualificationJpaEntity): Qualification
}