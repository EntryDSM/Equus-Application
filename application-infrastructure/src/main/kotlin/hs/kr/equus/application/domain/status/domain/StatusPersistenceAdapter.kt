package hs.kr.equus.application.domain.status.domain

import hs.kr.equus.application.domain.status.model.Status
import hs.kr.equus.application.domain.status.spi.StatusPort
import hs.kr.equus.application.global.feign.client.StatusClient
import org.springframework.stereotype.Component

@Component
class StatusPersistenceAdapter(
    private val statusClient: StatusClient
): StatusPort {
    override fun queryStatusByReceiptCode(receiptCode: Long): Status? {
        return statusClient.getStatusByReceiptCode(receiptCode)?.let {
            Status(
                id = it.id,
                isPrintsArrived = it.isPrintsArrived,
                examCode = it.examCode,
                isFirstRoundPass = it.isFirstRoundPass,
                isSecondRoundPass = it.isSecondRoundPass,
                receiptCode = it.receiptCode
            )
        }
    }
}