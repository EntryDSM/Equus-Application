package hs.kr.equus.application.domain.score.model

import java.util.UUID

abstract class ApplicationCase(
    receiptCode: Long,
) {
    val id: UUID = UUID(0, 0)
}
