package hs.kr.equus.application.domain.user.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class UserExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    companion object {}
}
