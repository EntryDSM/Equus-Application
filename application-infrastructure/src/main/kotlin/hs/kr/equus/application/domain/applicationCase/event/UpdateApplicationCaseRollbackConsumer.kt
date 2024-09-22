package hs.kr.equus.application.domain.applicationCase.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UpdateApplicationCaseRollbackConsumer(
    private val objectMapper: ObjectMapper,
    private val commandApplicationCasePort: CommandApplicationCasePort
) {
    @KafkaListener(
        topics = [KafkaTopics.UPDATE_EDUCATIONAL_STATUS],
        groupId = "rollback-application-case",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun changeApplicationCase(message: String) {
        val applicationCase = objectMapper.readValue(message, ApplicationCase::class.java)
        commandApplicationCasePort.save(applicationCase)
    }
}