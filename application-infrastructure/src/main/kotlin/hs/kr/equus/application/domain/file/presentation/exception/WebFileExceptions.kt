package hs.kr.equus.application.domain.file.presentation.exception

import hs.kr.equus.application.global.exception.WebException

sealed class WebFileExceptions(
    override val status: Int,
    override val message: String,
) : WebException(status, message) {
    class InvalidExtension(message: String = INVALID_EXTENSION) : WebFileExceptions(400, message)

    companion object {
        private const val INVALID_EXTENSION = "확장자가 유효하지 않습니다."
    }
}
