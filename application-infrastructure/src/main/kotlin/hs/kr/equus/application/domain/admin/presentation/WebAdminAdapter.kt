package hs.kr.equus.application.domain.admin.presentation

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.usecase.GetApplicationCountUseCase
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationCountResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationResponse
import hs.kr.equus.application.domain.application.usecase.GetApplicationUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class WebAdminAdapter(
    private val getApplicationCountUseCase: GetApplicationCountUseCase,
    private val getApplicationUseCase: GetApplicationUseCase
) {
    @GetMapping("/application-count") //todo 이걸 아예 통계쪽으로 빼야할수도?
    fun getApplicationCount(): GetApplicationCountResponse {
        return getApplicationCountUseCase.execute(
            applicationType = ApplicationType.COMMON,
            isDaejeon = true,
        )
    }

    @GetMapping("/application/{receipt-code}")
    fun getApplication(@PathVariable("receipt-code") receiptCode: Long): GetApplicationResponse {
        return getApplicationUseCase.execute(receiptCode)
    }
}
