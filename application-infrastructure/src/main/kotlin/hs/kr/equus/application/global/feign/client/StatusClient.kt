package hs.kr.equus.application.global.feign.client

import hs.kr.equus.application.global.feign.client.dto.response.StatusInfoElement
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping


@FeignClient(name = "StatusClient", url = "\${url.status}", fallback = StatusFallback::class)
interface StatusClient {
    @GetMapping("/status/list")
    fun getStatusList(): List<StatusInfoElement>
}

@Component
internal class StatusFallback : StatusClient {
    override fun getStatusList(): List<StatusInfoElement> {
        return ArrayList()
    }
}