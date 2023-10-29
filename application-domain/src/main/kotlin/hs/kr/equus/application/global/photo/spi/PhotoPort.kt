package hs.kr.equus.application.global.photo.spi

import java.io.File

interface PhotoPort {
    fun getPhotoUrl(photoFileName: String): String
    fun upload(file: File): String
    fun delete(photoFileName: String, path: String)
}