package hs.kr.equus.application.domain.application.spi.dto

data class PagedResult<T>(
    val items: List<T>,
    val hasNextPage: Boolean
)
