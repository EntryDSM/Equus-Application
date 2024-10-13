package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.user.model.User
import hs.kr.equus.application.domain.user.model.UserCache
import java.util.UUID

interface ApplicationQueryUserPort {
    fun queryUserByUserId(userId: UUID): User?

    fun queryUserByUserIdInCache(userId: UUID): UserCache?
}
