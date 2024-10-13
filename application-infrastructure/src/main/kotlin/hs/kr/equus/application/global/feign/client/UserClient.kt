package hs.kr.equus.application.global.feign.client

import hs.kr.equus.application.domain.status.spi.StatusPort
import hs.kr.equus.application.domain.user.model.UserCache
import hs.kr.equus.application.domain.user.spi.UserPort
import hs.kr.equus.application.global.feign.client.dto.response.StatusInfoElement
import hs.kr.equus.application.global.feign.client.dto.response.UserInfoElement
import hs.kr.equus.application.global.security.jwt.UserRole
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@FeignClient(name = "UserClient", url = "\${url.user}", fallback = UserFallBack::class)
interface UserClient {
    @GetMapping("/user/{userId}")
    fun getUserInfoByUserId(
        @PathVariable("userId") userId: UUID,
    ): UserInfoElement?

}

@Component
class UserFallBack(
    @Lazy private val userPort: UserPort
) : UserClient {

    override fun getUserInfoByUserId(userId: UUID): UserInfoElement? {
        val user = userPort.queryUserByUserIdInCache(userId)
        return user?.let {
            UserInfoElement(
                id = it.id,
                isParent = it.isParent,
                phoneNumber = it.phoneNumber,
                name = it.name,
                role = UserRole.valueOf(it.role)
            )
        } ?: UserInfoElement(
            id = userId,
            phoneNumber = "",
            name = "",
            isParent = false,
            role = UserRole.USER
        )
    }
}
