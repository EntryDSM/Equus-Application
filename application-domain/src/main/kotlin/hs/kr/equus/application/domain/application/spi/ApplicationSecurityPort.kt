package hs.kr.equus.application.domain.application.spi

import java.util.UUID

interface ApplicationSecurityPort {
    fun getCurrentUserId(): UUID
}
