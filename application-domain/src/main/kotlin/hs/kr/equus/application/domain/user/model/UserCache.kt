package hs.kr.equus.application.domain.user.model

import java.util.*

data class UserCache (
    val id: UUID,
    val phoneNumber: String,
    val name: String,
    val isParent: Boolean,
    val receiptCode: Long?,
    val role: String,
    val ttl: Long
)