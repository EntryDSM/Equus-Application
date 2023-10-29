package hs.kr.equus.application.global.security.spi

import java.util.*

interface SecurityPort {
    fun getCurrentUserId(): UUID
}
