package hs.kr.equus.application.domain.score.presentation

import hs.kr.equus.application.domain.score.usecase.QueryStaticsScoreUseCase
import hs.kr.equus.application.domain.score.usecase.dto.response.GetScoreResposne
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/score")
class WebScoreAdapter(
    private val queryStaticsScoreUseCase: QueryStaticsScoreUseCase
) {
    @GetMapping
    fun queryStaticsScore(): List<GetScoreResposne> {

        return queryStaticsScoreUseCase.execute()
    }
}