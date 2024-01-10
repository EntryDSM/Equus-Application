package hs.kr.equus.application.domain.graduationInfo.spi

interface QueryGraduationInfoIdPort {
    fun getGraduationInfoByCode(receiptCode: Long)
}
