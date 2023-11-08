package hs.kr.equus.application.global.feign.client

import hs.kr.equus.application.global.feign.client.dto.response.SchoolInfoElement
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "SchoolClient", url = "\${url.school}")
interface SchoolClient {
    @GetMapping
    fun getSchoolBySchoolCode(
        @RequestParam("SD_SCHUL_CODE") code: String,
        @RequestParam("KEY") key: String = "\${neis.key}",
        @RequestParam("Type") type: String = "json",
        @RequestParam("pIndex") pageIndex: Int = 1,
        @RequestParam("pSize") pageSize: Int = 100,
        @RequestParam("SCHUL_KND_SC_NM") schoolKind: String = "중학교",
    ): SchoolInfoElement?
}
