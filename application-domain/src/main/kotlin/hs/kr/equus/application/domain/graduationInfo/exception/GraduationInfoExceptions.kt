package hs.kr.equus.application.domain.graduationInfo.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class GraduationInfoExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class InvalidGraduateDate(message: String = INVALID_GRADUATE_DATE) :
        GraduationInfoExceptions(400, message)
    class EducationalStatusUnmatchedException(message: String = EDUCATIONAL_STATUS_UNMATCHED) :
        GraduationInfoExceptions(400, message)

    companion object {
        private const val INVALID_GRADUATE_DATE = "졸업일이 잘못되었습니다"
        private const val EDUCATIONAL_STATUS_UNMATCHED = "졸업상태가 일치하지 않습니다"
    }
}
