package hs.kr.equus.application.domain.admin.presentation

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.usecase.*
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationCountResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationResponse
import hs.kr.equus.application.domain.application.usecase.dto.request.GetApplicantsRequest
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicantsResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetStaticsCountResponse
import hs.kr.equus.application.domain.score.usecase.QueryStaticsScoreUseCase
import hs.kr.equus.application.domain.score.usecase.dto.response.GetStaticsScoreResponse
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
    private val queryStaticsScoreUseCase: QueryStaticsScoreUseCase,
    private val printApplicationInfoUseCase: PrintApplicationInfoUseCase,
    private val printApplicationCheckListUseCase: PrintApplicationCheckListUseCase,
    private val printAdmissionTicketUseCase: PrintAdmissionTicketUseCase
) {

    @GetMapping("/statics/score")
    fun queryStaticsScore(): List<GetStaticsScoreResponse> =
        queryStaticsScoreUseCase.execute()

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

    @GetMapping("/excel/applicants")
    fun printApplicationInfo(httpServletResponse: HttpServletResponse) =
        printApplicationInfoUseCase.execute(httpServletResponse)

    @GetMapping("/excel/applicants/check-list")
    fun printApplicationCheckList(httpServletResponse: HttpServletResponse) =
        printApplicationCheckListUseCase.execute(httpServletResponse)

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

    @GetMapping("/excel/admission-ticket")
    fun printAdmissionTicket(httpServletResponse: HttpServletResponse) =
        printAdmissionTicketUseCase.execute(httpServletResponse)
}
