package hs.kr.equus.application.domain.status.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class StatusExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class StatusNotFoundException(message: String = STATUS_NOT_FOUND) :
        StatusExceptions(404, message)

    class AlreadySubmittedException(message: String = ALREADY_SUBMITTED) :
        StatusExceptions(409, message)

    companion object {
        private const val STATUS_NOT_FOUND = "상태가 존재하지 않습니다"
        private const val ALREADY_SUBMITTED = "이미 최종제출이 되어있습니다."
    }
}
