package hs.kr.equus.application.domain.applicationCase.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.applicationCase.event.spi.ApplicationCaseEventPort
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ApplicationCaseProducer(
    private val updateApplicationCaseTemplate: KafkaTemplate<String, Any>,
    private val objectMapper: ObjectMapper
) : ApplicationCaseEventPort {
    override fun updateApplicationCase(originApplicationCase: ApplicationCase) {
        updateApplicationCaseTemplate.send(
            KafkaTopics.UPDATE_APPLICATION_CASE,
            objectMapper.writeValueAsString(originApplicationCase),
        )
    }

    override fun updateApplicationCaseRollback(originApplicationCase: ApplicationCase) {
        updateApplicationCaseTemplate.send(
            KafkaTopics.UPDATE_APPLICATION_CASE_ROLLBACK,
            objectMapper.writeValueAsString(originApplicationCase),
        )
    }
}
