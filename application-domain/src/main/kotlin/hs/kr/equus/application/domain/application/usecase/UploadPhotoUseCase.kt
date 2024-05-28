package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.file.spi.DeleteFilePort
import hs.kr.equus.application.domain.file.spi.UploadFilePort
import hs.kr.equus.application.domain.file.usecase.`object`.PathList
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort
import java.io.File

@UseCase
class UploadPhotoUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val commandApplicationPort: CommandApplicationPort,
    private val uploadFilePort: UploadFilePort,
    private val deleteFilePort: DeleteFilePort
    ) {
    fun execute(file: File): String? {

        val user = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(user)

        val photo = application?.photoPath.let {
            deleteFilePort.delete(application?.photoPath!!)
            uploadFilePort.upload(file, PathList.PHOTO)
        }

        commandApplicationPort.save(
            application!!.copy(
                photoPath = photo
            )
        )

        return photo
    }
}