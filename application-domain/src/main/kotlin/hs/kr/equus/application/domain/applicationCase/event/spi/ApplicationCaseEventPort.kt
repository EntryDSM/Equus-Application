package hs.kr.equus.application.domain.applicationCase.event.spi

interface ApplicationCaseEventPort {
    fun updateApplicationCase(receiptCode: Long)
}