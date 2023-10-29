package hs.kr.equus.application.domain.school.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class SchoolExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class InvalidSchoolTypeException(message: String = INVALID_SCHOOL_TYPE) :
        SchoolExceptions(401, message)
    class SchoolNotFoundException(message: String = SCHOOL_NOT_FOUND) :
        SchoolExceptions(404, message)

    companion object {
        private const val INVALID_SCHOOL_TYPE = "고등학교가 아닙니다"
        private const val SCHOOL_NOT_FOUND = "해당 고등학교가 존재하지 않습니다"
    }
}
