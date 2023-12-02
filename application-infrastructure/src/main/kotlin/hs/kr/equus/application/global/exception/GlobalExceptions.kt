package hs.kr.equus.application.global.exception

sealed class GlobalExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class InternalServerErrorException(message: String = INTERNAL_SERVER_ERROR) : GlobalExceptions(500, message)

    class ExpiredTokenException(message: String = EXPIRED_TOKEN) : GlobalExceptions(401, message)

    class InvalidTokenException(message: String = INVALID_TOKEN) : GlobalExceptions(401, message)

    companion object {
        private const val INTERNAL_SERVER_ERROR = "서버 에러가 발생하였습니다"
        private const val EXPIRED_TOKEN = "토큰이 만료되었습니다"
        private const val INVALID_TOKEN = "잘못된 토큰이 유효하지 않습니다"
    }
}
