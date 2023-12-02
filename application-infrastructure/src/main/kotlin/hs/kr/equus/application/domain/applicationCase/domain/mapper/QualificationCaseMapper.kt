package hs.kr.equus.application.domain.applicationCase.domain.mapper

import hs.kr.equus.application.domain.applicationCase.domain.entity.QualificationCaseJpaEntity
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.global.mapper.GenericMapper
import org.mapstruct.Mapper

@Mapper
abstract class QualificationCaseMapper : GenericMapper<QualificationCaseJpaEntity, QualificationCase> {
    abstract override fun toEntity(model: QualificationCase): QualificationCaseJpaEntity

    abstract override fun toDomain(entity: QualificationCaseJpaEntity?): QualificationCase?

    abstract override fun toDomainNotNull(entity: QualificationCaseJpaEntity): QualificationCase
}