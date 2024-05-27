package hs.kr.equus.application.domain.file.spi

interface DeleteFilePort {
    fun delete(path: String)
}