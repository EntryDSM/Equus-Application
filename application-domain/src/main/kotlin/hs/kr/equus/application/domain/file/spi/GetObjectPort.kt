package hs.kr.equus.application.domain.file.spi

interface GetObjectPort {
    fun getObject(fileName: String, path: String): ByteArray
}