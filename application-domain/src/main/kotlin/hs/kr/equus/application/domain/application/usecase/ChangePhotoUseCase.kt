package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.ChangeApplicationPhotoRequest
import hs.kr.equus.application.domain.file.exception.FileExceptions
import hs.kr.equus.application.domain.file.spi.CheckFilePort
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class ChangePhotoUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val checkFilePort: CheckFilePort,
    private val commandApplicationPort: CommandApplicationPort,
) {
    fun execute(request: ChangeApplicationPhotoRequest) {
        if (!checkFilePort.existsPath(request.path)) {
            throw FileExceptions.PathNotFound()
        }

        val userId = securityPort.getCurrentUserId()
        val application =
            queryApplicationPort.queryApplicationByUserId(userId)
                ?: throw ApplicationExceptions.ApplicationNotFoundException()

        commandApplicationPort.save(
            application.copy(
                photoPath = request.path,
            ),
        )
    }
}
