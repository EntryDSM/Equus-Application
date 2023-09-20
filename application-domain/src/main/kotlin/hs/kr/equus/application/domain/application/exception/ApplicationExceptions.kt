package hs.kr.equus.application.domain.application.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class ApplicationExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message,) {
    companion object {}
}
