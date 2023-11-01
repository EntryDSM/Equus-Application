package hs.kr.equus.application.global.mapper

interface GenericMapper<E, D> {
    fun toEntity(model: D): E

    fun toDomain(entity: E?): D?

    fun toDomainNotNull(entity: E): D
}
