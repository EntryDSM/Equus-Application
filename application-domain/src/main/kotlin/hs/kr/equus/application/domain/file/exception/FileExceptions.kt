package hs.kr.equus.application.domain.file.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class FileExceptions(
    override val status: Int,
    override val message: String
) : BusinessException(status, message) {

    // 400
    class NotValidContent(message: String = NOT_VALID_CONTENT) : FileExceptions(400, message)

    // 404
    class PathNotFound(message: String = PATH_NOT_FOUND) : FileExceptions(404, message)

    // 500
    class IOInterrupted(message: String = IO_INTERRUPTED) : FileExceptions(500, message)

    companion object {
        private const val NOT_VALID_CONTENT = "파일의 내용이 올바르지 않습니다."
        private const val PATH_NOT_FOUND = "휴무일을 찾을 수 없습니다."
        private const val IO_INTERRUPTED = "파일 입출력 처리가 중단되었습니다."
    }
}
