package hs.kr.equus.application.domain.file.spi

interface GenerateFileUrlPort {
    fun generateFileUrl(fileName: String, path: String): String
}