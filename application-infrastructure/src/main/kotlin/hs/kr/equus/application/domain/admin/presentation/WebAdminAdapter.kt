package hs.kr.equus.application.domain.admin.presentation

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.usecase.GetApplicationCountUseCase
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationCountResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationResponse
import hs.kr.equus.application.domain.application.usecase.GetApplicationUseCase
import hs.kr.equus.application.global.excel.generator.PrintApplicantCodesGenerator
import hs.kr.equus.application.domain.application.usecase.QueryStaticsCountUseCase
import hs.kr.equus.application.domain.application.usecase.dto.response.GetStaticsCountResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/admin")
class WebAdminAdapter(
    private val getApplicationCountUseCase: GetApplicationCountUseCase,
    private val getApplicationUseCase: GetApplicationUseCase,
    private val printApplicantCodesGenerator: PrintApplicantCodesGenerator
    private val queryStaticsCountUseCase: QueryStaticsCountUseCase
) {
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

    @GetMapping("/application/{receipt-code}")
    fun getApplication(@PathVariable("receipt-code") receiptCode: Long): GetApplicationResponse {
        return getApplicationUseCase.execute(receiptCode)
    }

    @GetMapping("/excel/applicants/code")
    fun printApplicantCodes(httpServletResponse: HttpServletResponse) =
        printApplicantCodesGenerator.execute(httpServletResponse)
}
