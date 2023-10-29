package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application
import java.util.UUID

interface QueryApplicationPort {
    fun queryApplicationByUserId(userId: UUID): Application?

    fun isExistsApplicationByUserId(userId: UUID): Boolean
}
