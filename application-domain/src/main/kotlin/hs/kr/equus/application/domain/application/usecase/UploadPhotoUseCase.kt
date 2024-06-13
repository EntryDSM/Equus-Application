package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.file.FilePathList
import hs.kr.equus.application.domain.file.spi.UploadFilePort
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort
import java.io.File

@UseCase
class UploadPhotoUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
    private val uploadFilePort: UploadFilePort,
    ) {
    fun execute(file: File): String {

        val user = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(user)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        val photo = uploadFilePort.upload(file, FilePathList.APPLICATION)

        commandApplicationPort.save(
            application.copy(
                photoPath = photo
            )
        )

        return photo
    }
}