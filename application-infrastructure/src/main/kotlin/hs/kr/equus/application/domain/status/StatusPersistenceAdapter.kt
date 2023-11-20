package hs.kr.equus.application.domain.status

import hs.kr.equus.application.domain.status.model.Status
import hs.kr.equus.application.domain.status.spi.StatusPort
import hs.kr.equus.application.global.kafka.dto.UpdateStatusEventRequest
import hs.kr.equus.application.global.kafka.producer.UpdateStatusProducer
import org.springframework.stereotype.Component

@Component
class StatusPersistenceAdapter(
    private val updateStatusProducer: UpdateStatusProducer,
) : StatusPort {
    override fun save(status: Status) {
        updateStatusProducer.send(
            status.run {
                UpdateStatusEventRequest(
                    isPrintsArrived = isPrintsArrived,
                    submittedAt = submittedAt,
                    examCode = examCode,
                    isFirstRoundPass = isFirstRoundPass,
                    isSecondRoundPass = isSecondRoundPass,
                    receiptCode = receiptCode,
                )
            },
        )
    }
}
