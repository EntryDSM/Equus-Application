package hs.kr.equus.application.domain.application.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class ApplicationExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class ApplicationNotFoundException(message: String = APPLICATION_NOT_FOUND) :
        ApplicationExceptions(404, message)

    class ApplicationExistsException(message: String = APPLICATION_EXISTS) :
        ApplicationExceptions(409, message)

    class InvalidApplicationRemarkException(message: String = INVALID_APPLICATION_REMARK) :
        ApplicationExceptions(401, message)

    class ApplicationProcessNotComplete(message: String = APPLICATION_PROCESS_NOT_COMPLETE) :
        ApplicationExceptions(400, message)

    class InvalidKeywordException(message: String = INVALID_KEYWORD) :
        ApplicationExceptions(400, message)

    companion object {
        private const val APPLICATION_NOT_FOUND = "해당 원서가 존재하지 않습니다"
        private const val APPLICATION_EXISTS = "원서가 이미 존재합니다"
        private const val INVALID_APPLICATION_REMARK = "선택한 전형과 맞지 않는 특기사항입니다"
        private const val APPLICATION_PROCESS_NOT_COMPLETE = "원서접수 과정이 모두 끝나지 않았습니다"
        private const val INVALID_KEYWORD = "유효하지 않은 키워드 입니다."
    }
}
