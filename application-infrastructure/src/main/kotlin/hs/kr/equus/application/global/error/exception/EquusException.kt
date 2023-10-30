package hs.kr.equus.application.global.error.exception

abstract class EquusException(
    val errorCode: ErrorCode
) : RuntimeException()