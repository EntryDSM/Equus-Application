package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.spi.ApplicationCommandStatusPort
import hs.kr.equus.application.domain.application.spi.ApplicationQueryUserPort
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.status.model.Status
import hs.kr.equus.application.domain.user.model.User
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class CreateApplicationUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
    private val applicationQueryUserPort: ApplicationQueryUserPort,
    private val applicationCommandStatusPort: ApplicationCommandStatusPort,
) {
    fun execute() {
        val userId = securityPort.getCurrentUserId()
        val user = applicationQueryUserPort.queryUserByUserId(userId)
        if (queryApplicationPort.isExistsApplicationByUserId(userId)) {
            throw ApplicationExceptions.ApplicationExistsException()
        }
        val receiptCode =
            commandApplicationPort.save(createWithUserInfo(user)).receiptCode

        applicationCommandStatusPort.save(
            Status(receiptCode = receiptCode),
        )
    }

    fun createWithUserInfo(user: User): Application {
        return user.run {
            if (isParent) {
                Application(
                    parentName = name,
                    parentTel = phoneNumber,
                    userId = id,
                )
            } else {
                Application(
                    applicantName = name,
                    applicantTel = phoneNumber,
                    userId = id,
                )
            }
        }
    }
}
