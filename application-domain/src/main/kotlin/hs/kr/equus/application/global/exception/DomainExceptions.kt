package hs.kr.equus.application.global.exception

sealed class DomainExceptions {

    // 500
    class NotInitializationProperties(message: String = NOT_INITIALIZATION_PROPERTIES) : BusinessException(500, message)

    companion object {
        private const val NOT_INITIALIZATION_PROPERTIES = "Domain Properties 초기화 실패"
    }
}
