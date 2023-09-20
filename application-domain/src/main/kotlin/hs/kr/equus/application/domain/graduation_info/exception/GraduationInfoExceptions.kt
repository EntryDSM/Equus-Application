package hs.kr.equus.application.domain.graduation_info.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class GraduationInfoExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    companion object {}
}
