package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import javax.servlet.http.HttpServletResponse

@ReadOnlyUseCase
class PrintApplicationInfoUseCase(
    private val printApplicationInfoPort: PrintApplicationInfoPort,
    private val queryApplicationInfoListByStatusIsSubmittedTruePort: QueryApplicationInfoListByStatusIsSubmittedTruePort,
) {
    fun execute(httpServletResponse: HttpServletResponse) {
        val application = queryApplicationInfoListByStatusIsSubmittedTruePort.queryApplicationInfoListByStatusIsSubmittedTrue()
        printApplicationInfoPort.execute(httpServletResponse, application)
    }
}