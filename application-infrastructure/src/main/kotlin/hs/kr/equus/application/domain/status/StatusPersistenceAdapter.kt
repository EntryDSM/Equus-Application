package hs.kr.equus.application.domain.status

import hs.kr.equus.application.domain.status.model.Status
import hs.kr.equus.application.domain.status.spi.StatusPort
import hs.kr.equus.application.global.kafka.dto.CreateStatusEventRequest
import hs.kr.equus.application.global.kafka.producer.CreateStatusProducer
import org.springframework.stereotype.Component

@Component
class StatusPersistenceAdapter(
    private val createStatusProducer: CreateStatusProducer,
) : StatusPort {
    override fun save(status: Status) {
        createStatusProducer.send(
            status.run {
                CreateStatusEventRequest(
                    isPrintsArrived = isPrintsArrived,
                    submittedAt = submittedAt,
                    examCode = examCode,
                    isFirstRoundPass = isFirstRoundPass,
                    isSecondRoundPass = isSecondRoundPass,
                    receiptCode = receiptCode,
                )
            }
        )
    }
}
