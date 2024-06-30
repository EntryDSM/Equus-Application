package hs.kr.equus.application.domain.admin.presentation

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.usecase.GetApplicantsUseCase
import hs.kr.equus.application.domain.application.usecase.GetApplicationCountUseCase
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationCountResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationResponse
import hs.kr.equus.application.domain.application.usecase.GetApplicationUseCase
import hs.kr.equus.application.domain.application.usecase.dto.request.GetApplicantsRequest
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicantsResponse
import hs.kr.equus.application.domain.application.usecase.PrintApplicantCodesUseCase
import hs.kr.equus.application.global.excel.generator.PrintApplicantCodesGenerator
import hs.kr.equus.application.domain.application.usecase.QueryStaticsCountUseCase
import hs.kr.equus.application.domain.application.usecase.dto.response.GetStaticsCountResponse
import hs.kr.equus.application.domain.score.presentation.dto.reponse.GetScoreResponse
import hs.kr.equus.application.domain.score.usecase.QueryStaticsScoreUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/admin/application")
class WebAdminAdapter(
    private val getApplicationCountUseCase: GetApplicationCountUseCase,
    private val getApplicationUseCase: GetApplicationUseCase,
    private val getApplicantsUseCase: GetApplicantsUseCase,
    private val printApplicantCodesUseCase: PrintApplicantCodesUseCase,
    private val queryStaticsCountUseCase: QueryStaticsCountUseCase,
    private val queryStaticsScoreUseCase: QueryStaticsScoreUseCase
) {

    @GetMapping("/statics/score")
    fun queryStaticsScore(): List<GetScoreResponse> =
        queryStaticsScoreUseCase.execute().map {
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

    @GetMapping("/statics/count")
    fun queryStaticsCount(): List<GetStaticsCountResponse> =
        queryStaticsCountUseCase.execute()

    @GetMapping("/application-count") //todo 이걸 아예 통계쪽으로 빼야할수도?
    fun getApplicationCount(): GetApplicationCountResponse {
        return getApplicationCountUseCase.execute(
            applicationType = ApplicationType.COMMON,
            isDaejeon = true,
        )
    }

    @GetMapping("/{receipt-code}")
    fun getApplication(@PathVariable("receipt-code") receiptCode: Long): GetApplicationResponse {
        return getApplicationUseCase.execute(receiptCode)
    }

    @GetMapping("/excel/applicants/code")
    fun printApplicantCodes(httpServletResponse: HttpServletResponse) =
        printApplicantCodesUseCase.execute(httpServletResponse)

    @GetMapping("/applicants")
    fun getApplicants(
        @RequestParam(name = "offset", defaultValue = "10")
        pageSize: Long,
        @RequestParam(name = "pageSize", defaultValue = "0")
        offset: Long,
        @ModelAttribute
        getApplicantsRequest: GetApplicantsRequest
    ): GetApplicantsResponse {
        return getApplicantsUseCase.execute(pageSize, offset, getApplicantsRequest)
    }
}
