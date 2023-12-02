package hs.kr.equus.application.domain.application.event.spi

interface ApplicationEventPort {
    fun create(receiptCode: Long)

    fun updateEducationalStatus(receiptCode: Long)
}
