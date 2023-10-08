package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.user.model.User
import java.util.UUID

interface ApplicationQueryUserPort {
    fun queryUserByUserId(userId: UUID): User
}
