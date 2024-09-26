package hs.kr.equus.application.domain.applicationCase.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class ApplicationCaseExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class EducationalStatusUnmatchedException(message: String = EDUCATIONAL_STATUS_UNMATCHED) :
        ApplicationCaseExceptions(400, message)

    class ApplicationCaseNotFoundException(message: String = APPLICATION_CASE_NOT_FOUND) :
        ApplicationCaseExceptions(404, message)

    companion object {
        private const val EDUCATIONAL_STATUS_UNMATCHED = "졸업상태가 일치하지 않습니다"
        private const val APPLICATION_CASE_NOT_FOUND = "Application Case가 존재하지 않습니다"
    }
}