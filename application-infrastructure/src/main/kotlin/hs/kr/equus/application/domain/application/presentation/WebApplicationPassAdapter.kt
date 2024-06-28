package hs.kr.equus.application.domain.application.presentation

import hs.kr.equus.application.domain.application.usecase.QueryIsFirstRoundPassUseCase
import hs.kr.equus.application.domain.application.usecase.dto.response.QueryIsFirstRoundPassResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pass")
class WebApplicationPassAdapter(
    private val queryIsFirstRoundPassUseCase: QueryIsFirstRoundPassUseCase
) {
    @GetMapping("/first-round")
    fun queryIsFirstRound(): QueryIsFirstRoundPassResponse =
        queryIsFirstRoundPassUseCase.execute()
}