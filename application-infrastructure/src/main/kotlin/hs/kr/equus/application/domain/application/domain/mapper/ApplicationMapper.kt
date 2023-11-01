package hs.kr.equus.application.domain.application.domain.mapper

import hs.kr.equus.application.domain.application.domain.entity.ApplicationJpaEntity
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.global.mapper.GenericMapper
import org.mapstruct.Mapper

@Mapper
abstract class ApplicationMapper : GenericMapper<ApplicationJpaEntity, Application> {
    abstract override fun toEntity(model: Application): ApplicationJpaEntity

    abstract override fun toDomain(entity: ApplicationJpaEntity?): Application?

    abstract override fun toDomainNotNull(entity: ApplicationJpaEntity): Application
}