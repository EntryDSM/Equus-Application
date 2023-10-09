package hs.kr.equus.application.domain.graduationInfo.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class GraduationInfoExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {

    class InvalidGraduateDate(message: String = INVALID_GRADUATE_DATE) : GraduationInfoExceptions(404, message)

    companion object {
        private const val INVALID_GRADUATE_DATE = "졸업일이 잘못되었습니다"
    }
}
