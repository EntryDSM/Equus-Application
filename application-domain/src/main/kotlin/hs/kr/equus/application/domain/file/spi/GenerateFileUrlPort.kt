package hs.kr.equus.application.domain.file.spi

interface GenerateFileUrlPort {
    fun generateFileUrl(filePath: String): String
}