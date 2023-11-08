package hs.kr.equus.application.global.feign.exception

import hs.kr.equus.application.global.exception.BusinessException

sealed class FeignExceptions(
    override val status: Int,
    override val message: String,
) : BusinessException(status, message) {
    class FeignServerErrorException(message: String = FEIGN_SERVER_ERROR) : FeignExceptions(500, message)

    companion object {
        private const val FEIGN_SERVER_ERROR = "통신 서버 오류"
    }
}
