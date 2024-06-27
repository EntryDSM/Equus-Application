package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.PrintApplicantCodesPort
import hs.kr.equus.application.domain.application.spi.QueryApplicantCodesByIsFirstRoundPassPort
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import javax.servlet.http.HttpServletResponse

@ReadOnlyUseCase
class PrintApplicantCodesUseCase(
    private val printApplicantCodesPort: PrintApplicantCodesPort,
    private val queryApplicantCodesByIsFirstRoundPassPort: QueryApplicantCodesByIsFirstRoundPassPort
) {
    fun execute(httpServletResponse: HttpServletResponse) {
        val applicantCodes = queryApplicantCodesByIsFirstRoundPassPort.queryApplicantCodesByIsFirstRoundPass()
        printApplicantCodesPort.execute(httpServletResponse, applicantCodes)
    }
}