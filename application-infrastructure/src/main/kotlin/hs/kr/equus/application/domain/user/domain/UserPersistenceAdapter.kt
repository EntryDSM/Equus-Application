package hs.kr.equus.application.domain.user.domain

import hs.kr.equus.application.domain.user.model.User
import hs.kr.equus.application.domain.user.spi.UserPort
import hs.kr.equus.application.global.feign.client.UserClient
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserPersistenceAdapter(
    private val userClient: UserClient,
) : UserPort {
    override fun queryUserByUserId(userId: UUID): User {
        return userClient.getUserInfoByUserId(userId).run {
            User(
                id = id,
                phoneNumber = phoneNumber,
                name = name,
                isParent = isParent,
            )
        }
    }
}
