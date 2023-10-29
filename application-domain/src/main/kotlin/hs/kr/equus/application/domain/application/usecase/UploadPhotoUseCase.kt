package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.photo.spi.PhotoPort
import hs.kr.equus.application.global.security.spi.SecurityPort
import java.io.File

@UseCase
class UploadPhotoUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val photoPort: PhotoPort,
    private val commandApplicationPort: CommandApplicationPort,
) {
    fun execute(file: File) {
        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)

        application.photoFileName?.let {
            photoPort.delete(application.photoFileName, "/entry_info") // todo 경로 상수로 바꿔야함
        }

        commandApplicationPort.save(
            application.copy(
                photoFileName = photoPort.upload(file),
            )
        )
    }
}
