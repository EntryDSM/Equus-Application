package hs.kr.equus.application.global.feign.client

import hs.kr.equus.application.global.feign.client.dto.response.UserInfoElement
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@FeignClient(name = "UserClient", url = "\${url.user}")
interface UserClient {
    @GetMapping("/users/{userId}")
    fun getUserInfoByUserId(
        @PathVariable("userId") userId: UUID
    ): UserInfoElement // TODO 현수한테 왜 쿼리파람했냐 묻기
}
