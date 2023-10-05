package hs.kr.equus.application.domain.graduationInfo.model

import java.util.*

abstract class GraduationInfo(
    receiptCode: Long,
) {
    val id: UUID = UUID(0, 0)
}
