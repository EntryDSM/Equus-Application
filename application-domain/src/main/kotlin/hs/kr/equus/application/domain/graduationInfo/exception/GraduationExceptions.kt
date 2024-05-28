package hs.kr.equus.application.domain.graduationInfo.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class GraduationExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class GraduationNotFoundException(message: String = GRADUATION_NOT_FOUND) :
        GraduationExceptions(404, message)

    companion object {
        private const val GRADUATION_NOT_FOUND = "졸업을 찾을 수 없습니다."
    }
}
