package hs.kr.equus.application.domain.application.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.application.event.spi.ApplicationEventPort
import hs.kr.equus.application.domain.application.event.dto.UpdateEducationStatusEvent
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ApplicationProducer(
    private val mapper: ObjectMapper,
    private val createApplicationTemplate: KafkaTemplate<String, Any>,
    private val updateEducationalStatusTemplate: KafkaTemplate<String, Any>,
    private val submitApplicationFinalTemplate: KafkaTemplate<String, Any>
) : ApplicationEventPort {
    override fun create(receiptCode: Long) {
        createApplicationTemplate.send(
            KafkaTopics.CREATE_APPLICATION,
            receiptCode,
        )
    }

    override fun updateEducationalStatus(receiptCode: Long, graduateDate: LocalDate) {
        updateEducationalStatusTemplate.send(
            KafkaTopics.UPDATE_EDUCATIONAL_STATUS,
            UpdateEducationStatusEvent(receiptCode, graduateDate),
        )
    }

    override fun submitApplicationFinal(receiptCode: Long) {
        submitApplicationFinalTemplate.send(
            KafkaTopics.SUBMIT_APPLICATION_FINAL,
            receiptCode
        )
    }
}
