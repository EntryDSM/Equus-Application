package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.factory.ApplicationCaseFactory
import hs.kr.equus.application.domain.applicationCase.service.ApplicationCaseService
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
    private val applicationCaseService: ApplicationCaseService
) {
    fun execute(receiptCode: Long) {
        val application = queryApplicationCaseQueryApplicationPort.queryApplicationByReceiptCode(receiptCode)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val existingCase = queryApplicationCasePort.queryApplicationCaseByApplication(application)

        print("existingCase : " + existingCase.toString())

        val newApplicationCase = applicationCaseFactory.createApplicationCase(
            receiptCode,
            application.educationalStatus
        )

        when {
            existingCase == null -> {
                print("1번-----------------")
                commandApplicationCasePort.save(newApplicationCase)
            }
            applicationCaseService.hasEducationalStatusMismatch(application, existingCase) -> {
                print("2번-----------------")
                commandApplicationCasePort.delete(existingCase)
                commandApplicationCasePort.save(newApplicationCase)
            }
        }
    }
}
