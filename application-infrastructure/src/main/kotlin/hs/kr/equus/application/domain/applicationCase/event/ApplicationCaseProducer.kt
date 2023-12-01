package hs.kr.equus.application.domain.applicationCase.event

import hs.kr.equus.application.domain.applicationCase.event.spi.ApplicationCaseEventPort
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ApplicationCaseProducer(
    private val updateApplicationCaseTemplate: KafkaTemplate<String, Any>,
) : ApplicationCaseEventPort {
    override fun updateApplicationCase(receiptCode: Long) {
        updateApplicationCaseTemplate.send(
            KafkaTopics.UPDATE_APPLICATION_CASE,
            receiptCode,
        )
    }
}
