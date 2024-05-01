package hs.kr.equus.application.global.feign.client

import hs.kr.equus.application.global.feign.client.dto.response.StatusInfoElement
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@FeignClient(name = "StatusClient", url = "\${url.status}")
interface StatusClient {
    @GetMapping("/internal/status/list")
    fun getStatusList(): List<StatusInfoElement>

    @GetMapping("/internal/status/{receipt-code}")
    fun getStatusByReceiptCode(@PathVariable("receipt-code") receiptCode: Long): StatusInfoElement?
}

//@Component
//class StatusFallback : StatusClient {
//    override fun getStatusList(): List<StatusInfoElement> {
//        return ArrayList()
//    }
//
//    override fun getStatus(receiptCode: Long): StatusInfoElement? {
//        return null
//    }
//}