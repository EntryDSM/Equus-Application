package hs.kr.equus.application.domain.application.presentation

import hs.kr.equus.application.domain.application.usecase.QueryIsFirstRoundPassUseCase
import hs.kr.equus.application.domain.application.usecase.QueryIsSecondRoundPassUseCase
import hs.kr.equus.application.domain.application.usecase.dto.response.QueryIsFirstRoundPassResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.QueryIsSecondRoundPassResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pass")
class WebApplicationPassAdapter(
    private val queryIsFirstRoundPassUseCase: QueryIsFirstRoundPassUseCase,
    private val queryIsSecondRoundPassUseCase: QueryIsSecondRoundPassUseCase
) {
    @GetMapping("/first-round")
    fun queryIsFirstRound(): QueryIsFirstRoundPassResponse =
        queryIsFirstRoundPassUseCase.execute()

    @GetMapping("/second-round")
    fun queryIsSecondRound(): QueryIsSecondRoundPassResponse =
        queryIsSecondRoundPassUseCase.execute()
}
