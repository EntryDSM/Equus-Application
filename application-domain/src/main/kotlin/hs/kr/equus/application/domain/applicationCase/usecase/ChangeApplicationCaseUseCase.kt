package hs.kr.equus.application.domain.applicationCase.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.applicationCase.factory.ApplicationCaseFactory
import hs.kr.equus.application.domain.applicationCase.service.ApplicationCaseService
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.domain.graduationInfo.spi.CommandGraduationInfoPort
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class ChangeApplicationCaseUseCase(
    private val queryApplicationCaseQueryApplicationPort: ApplicationCaseQueryApplicationPort,
    private val commandApplicationCasePort: CommandApplicationCasePort,
    private val applicationCaseFactory: ApplicationCaseFactory,
    private val queryApplicationCasePort: QueryApplicationCasePort
) {
    fun execute(receiptCode: Long) {
        val application = queryApplicationCaseQueryApplicationPort.queryApplicationByReceiptCode(receiptCode)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        if(queryApplicationCasePort.isExistsApplicationCaseByApplication(application)) {
            commandApplicationCasePort.save(
                applicationCaseFactory.createApplicationCase(
                    receiptCode,
                    application.educationalStatus
                )
            )
        }
    }
}
