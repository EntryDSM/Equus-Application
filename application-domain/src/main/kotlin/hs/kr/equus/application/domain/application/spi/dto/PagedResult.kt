package hs.kr.equus.application.domain

data class PagedResult<T>(
    val items: List<T>,
    val hasNextPage: Boolean
)
