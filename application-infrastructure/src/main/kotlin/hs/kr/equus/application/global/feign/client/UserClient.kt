package hs.kr.equus.application.global.feign.client

import hs.kr.equus.application.global.feign.client.dto.response.UserInfoElement
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@FeignClient(name = "UserClient", url = "\${url.user}")
interface UserClient {
    @GetMapping("/user/{userId}")
    fun getUserInfoByUserId(
        @PathVariable("userId") userId: UUID,
    ): UserInfoElement
}
