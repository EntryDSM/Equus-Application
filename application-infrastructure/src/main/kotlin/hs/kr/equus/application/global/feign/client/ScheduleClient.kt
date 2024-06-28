package hs.kr.equus.application.global.feign.client

import hs.kr.equus.application.global.feign.FeignConfig
import hs.kr.equus.application.global.feign.client.dto.response.ScheduleInfoElement
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestPart

@FeignClient(name = "ScheduleClient", url = "\${url.schedule}", configuration = [FeignConfig::class])
interface ScheduleClient {
    @GetMapping(params = ["type"])
    fun queryScheduleByType(@RequestPart type: String): ScheduleInfoElement?
}