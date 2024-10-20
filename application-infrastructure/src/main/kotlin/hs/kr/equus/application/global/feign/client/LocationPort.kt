package hs.kr.equus.application.global.feign.client

import hs.kr.equus.application.global.feign.client.dto.response.LocationElement
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "LocationClient", url = "\${url.location}")
interface LocationPort {
    @GetMapping("/v2/local/search/address.json")
    fun getLocationInfo(
        @RequestParam("query") streetAddress: String,
        @RequestHeader("Authorization") kakaoAuthorization: String = "KakaoAK \${kakao.authorization}"
    ): LocationElement
}