package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.PrintAdmissionTicketPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import javax.servlet.http.HttpServletResponse

@ReadOnlyUseCase
class PrintAdmissionTicketUseCase(
    private val queryApplicationPort: QueryApplicationPort,
    private val printAdmissionTicketPort: PrintAdmissionTicketPort
){
    fun execute(httpServletResponse: HttpServletResponse) {
        val applications = queryApplicationPort.queryAllApplication()
        printAdmissionTicketPort.execute(httpServletResponse, applications)
    }
}