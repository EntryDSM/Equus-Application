package hs.kr.equus.application.global.feign.client.dto.response

import hs.kr.equus.application.global.security.jwt.UserRole
import java.util.*

data class UserInfoElement(
    val id: UUID,
    val phoneNumber: String,
    val name: String,
    val isParent: Boolean,
    val role: UserRole
)
