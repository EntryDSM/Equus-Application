package hs.kr.equus.application.domain.applicationCase.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class ApplicationCaseExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class EducationalStatusUnmatchedException(message: String = EDUCATIONAL_STATUS_UNMATCHED) :
        ApplicationCaseExceptions(400, message)

    companion object {
        private const val EDUCATIONAL_STATUS_UNMATCHED = "졸업상태가 일치하지 않습니다"
    }
}