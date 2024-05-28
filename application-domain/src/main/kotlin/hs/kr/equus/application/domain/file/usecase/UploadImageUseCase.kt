package hs.kr.equus.application.domain.file.usecase

import hs.kr.equus.application.domain.file.spi.UploadFilePort
import hs.kr.equus.application.domain.file.usecase.`object`.PathList
import hs.kr.equus.application.global.annotation.UseCase
import java.io.File

@UseCase
class UploadImageUseCase(
    private val uploadFilePort: UploadFilePort,
) {
    fun execute(file: File): String {
        return uploadFilePort.upload(file, PathList.PHOTO)
    }
}
