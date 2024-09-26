package hs.kr.equus.application.domain.application.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class DeleteApplicationConsumer(
    private val mapper: ObjectMapper,
    private val commandApplicationPort: CommandApplicationPort
) {
    @KafkaListener(
        topics = [KafkaTopics.DELETE_USER],
        groupId = "delete-application-consumer",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun deleteApplication(message: String) {
        val receiptCode = mapper.readValue(message, Long::class.java)
        commandApplicationPort.deleteByReceiptCode(receiptCode)
    }
}