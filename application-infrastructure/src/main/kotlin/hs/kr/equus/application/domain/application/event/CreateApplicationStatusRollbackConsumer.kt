package hs.kr.equus.application.domain.application.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.application.usecase.DeleteApplicationUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CreateApplicationStatusRollbackConsumer(
    private val objectMapper: ObjectMapper,
    private val deleteApplicationUseCase: DeleteApplicationUseCase
) {
    @KafkaListener(
        topics = [KafkaTopics.CREATE_APPLICATION_STATUS_ROLLBACK],
        groupId = "create-application-status-rollback",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun execute(message: String) {
        val receiptCode = objectMapper.readValue(message, Long::class.java)
        deleteApplicationUseCase.execute(receiptCode)
    }
}