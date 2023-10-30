package hs.kr.equus.application.global.exception

import hs.kr.equus.application.global.error.exception.EquusException
import hs.kr.equus.application.global.error.exception.ErrorCode

object InternalServerErrorException : EquusException(
    ErrorCode.INTERNAL_SERVER_ERROR
)