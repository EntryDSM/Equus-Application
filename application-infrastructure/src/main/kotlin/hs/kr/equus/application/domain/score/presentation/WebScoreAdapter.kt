package hs.kr.equus.application.domain.score.presentation

import hs.kr.equus.application.domain.score.presentation.dto.reponse.GetScoreResponse
import hs.kr.equus.application.domain.score.usecase.QueryStaticsScoreUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/score")
class WebScoreAdapter(
    private val queryStaticsScoreUseCase: QueryStaticsScoreUseCase
) {
    @GetMapping
    fun queryStaticsScore(): List<GetScoreResponse> {
        return queryStaticsScoreUseCase.execute().map {
            GetScoreResponse(
                applicationType = it.applicationType,
                isDaejeon = it.isDaejeon,
                score_79 = it.score_79,
                score80_92 = it.score80_92,
                score93_105 = it.score93_105,
                score106_118 = it.score106_118,
                score119_131 = it.score119_131,
                score132_144 = it.score132_144,
                score145_157 = it.score145_157,
                score158_170 = it.score158_170
            )
        }
    }
}
