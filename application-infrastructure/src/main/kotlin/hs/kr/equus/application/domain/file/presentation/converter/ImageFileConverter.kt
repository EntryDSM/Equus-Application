package hs.kr.equus.application.domain.file.presentation.converter

import hs.kr.equus.application.domain.file.presentation.converter.FileExtensions.HEIC
import hs.kr.equus.application.domain.file.presentation.converter.FileExtensions.JPEG
import hs.kr.equus.application.domain.file.presentation.converter.FileExtensions.JPG
import hs.kr.equus.application.domain.file.presentation.converter.FileExtensions.PNG
import org.springframework.web.multipart.MultipartFile

object ImageFileConverter : FileConverter {
    override fun isCorrectExtension(multipartFile: MultipartFile): Boolean {
        return when (multipartFile.extension) {
            JPG, JPEG, PNG, HEIC -> true
            else -> false
        }
    }
}
