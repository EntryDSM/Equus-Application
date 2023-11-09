package hs.kr.equus.application.global.security

import hs.kr.equus.application.global.security.spi.SecurityPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class SecurityAdapter : SecurityPort {
    override fun getCurrentUserId(): UUID {
        return UUID.fromString(SecurityContextHolder.getContext().authentication.name)
    }
}
