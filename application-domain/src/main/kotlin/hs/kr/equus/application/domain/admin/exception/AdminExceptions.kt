package hs.kr.equus.application.domain.admin.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class AdminExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    companion object {}
}
