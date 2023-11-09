package hs.kr.equus.application.domain.file.spi

interface CheckFilePort {
    fun existsPath(path: String): Boolean
}
