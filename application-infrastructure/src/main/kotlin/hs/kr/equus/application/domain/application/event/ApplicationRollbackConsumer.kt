package hs.kr.equus.application.domain.application.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.application.usecase.DeleteApplicationUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener

class ApplicationRollbackConsumer(
    private val objectMapper: ObjectMapper,
    private val deleteApplicationUseCase: DeleteApplicationUseCase
) {
    @KafkaListener(
        topics = [KafkaTopics.CREATE_APPLICATION_STATUS_ROLLBACK],
        groupId = "create-application-status-rollback",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun createStatusRollback(message: String) {
        val receiptCode = objectMapper.readValue(message, Long::class.java)
        deleteApplicationUseCase.execute(receiptCode)
    }

    @KafkaListener(
        topics = [KafkaTopics.CREATE_APPLICATION_SCORE_ROLLBACK],
        groupId = "create-score-rollback",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun createScoreRollback(message: String) {
        val receiptCode = objectMapper.readValue(message, Long::class.java)
        deleteApplicationUseCase.execute(receiptCode)
    }
}