package hs.kr.equus.application.domain.file.spi

import java.io.File

interface UploadFilePort {
    fun upload(file: File, pathList: String): String
}
