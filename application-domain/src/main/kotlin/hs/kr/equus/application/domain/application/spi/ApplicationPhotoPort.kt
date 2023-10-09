package hs.kr.equus.application.domain.application.spi

interface ApplicationPhotoPort {
    fun getPhotoUrl(photoFileName: String): String
}
