package hs.kr.equus.application.domain.application.spi

interface ApplicationCommandStatusPort {
    fun updateExamCode(receiptCode: Long, examCode: String)
}