package hs.kr.equus.application.global.exception

import hs.kr.equus.application.global.error.exception.EquusException
import hs.kr.equus.application.global.error.exception.ErrorCode

object InvalidTokenException : EquusException(
    ErrorCode.INVALID_TOKEN
)