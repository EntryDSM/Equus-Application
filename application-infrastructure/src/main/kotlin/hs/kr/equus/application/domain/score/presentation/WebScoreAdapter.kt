package hs.kr.equus.application.domain.score.presentation

import hs.kr.equus.application.domain.score.usecase.QueryMyTotalScoreUseCase
import hs.kr.equus.application.domain.score.usecase.dto.response.QueryTotalScoreResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/score")
class WebScoreAdapter(
    private val queryMyTotalScoreUseCase: QueryMyTotalScoreUseCase
) {
    @GetMapping
    fun queryMyTotalScore(): QueryTotalScoreResponse =
        queryMyTotalScoreUseCase.execute()
}
