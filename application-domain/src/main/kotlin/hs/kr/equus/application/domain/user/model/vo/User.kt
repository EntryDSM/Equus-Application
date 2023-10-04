package hs.kr.equus.application.domain.user.model.vo

import hs.kr.equus.application.global.annotation.domain.Aggregate
import java.util.*

@Aggregate
data class User(
    val id: UUID,
    val phoneNumber: String,
    val password: String,
    val name: String,
    val isParent: Boolean,
)