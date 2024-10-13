package hs.kr.equus.application.domain.user.domain

import hs.kr.equus.application.domain.user.domain.repository.UserCacheRepository
import hs.kr.equus.application.domain.user.model.User
import hs.kr.equus.application.domain.user.model.UserCache
import hs.kr.equus.application.domain.user.spi.UserPort
import hs.kr.equus.application.global.feign.client.UserClient
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserPersistenceAdapter(
    private val userClient: UserClient,
    private val userCacheRepository: UserCacheRepository
) : UserPort {
    override fun queryUserByUserId(userId: UUID): User? {
        return userClient.getUserInfoByUserId(userId)?.let {
            User(
                id = it.id,
                phoneNumber = it.phoneNumber,
                name = it.name,
                isParent = it.isParent,
            )
        }
    }

    override fun queryUserByUserIdInCache(userId: UUID): UserCache? {
        return userCacheRepository.findById(userId)
            .map {
                UserCache(
                    id = it.id,
                    phoneNumber = it.phoneNumber,
                    name = it.name,
                    isParent = it.isParent,
                    receiptCode = it.receiptCode,
                    role = it.role.name,
                    ttl = it.ttl
                )
            }.orElse(null)
    }
}
