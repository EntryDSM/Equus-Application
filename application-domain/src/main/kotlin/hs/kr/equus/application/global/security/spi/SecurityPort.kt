package hs.kr.equus.application.global.security.spi

import java.util.UUID

interface SecurityPort {
    fun getCurrentUserId(): UUID
}
