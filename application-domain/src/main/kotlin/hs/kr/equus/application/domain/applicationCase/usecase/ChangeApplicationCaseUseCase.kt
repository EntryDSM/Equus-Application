package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.factory.ApplicationCaseFactory
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class ChangeApplicationCaseUseCase(
    private val queryApplicationCasePort: QueryApplicationCasePort,
    private val queryApplicationCaseQueryApplicationPort: ApplicationCaseQueryApplicationPort,
    private val commandApplicationCasePort: CommandApplicationCasePort,
    private val applicationCaseFactory: ApplicationCaseFactory,
) {
    fun execute(receiptCode: Long) {
        val application = queryApplicationCaseQueryApplicationPort.queryApplicationByReceiptCode(receiptCode)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        queryApplicationCasePort.queryApplicationCaseByApplication(application)?.let {
            commandApplicationCasePort.delete(it)
        }

        val applicationCase = applicationCaseFactory.createApplicationCase(
            receiptCode,
            application.educationalStatus
        )

        commandApplicationCasePort.save(applicationCase)
    }
}
