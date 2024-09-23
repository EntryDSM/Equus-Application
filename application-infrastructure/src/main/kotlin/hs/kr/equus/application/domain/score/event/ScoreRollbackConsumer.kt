package hs.kr.equus.application.domain.score.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.score.usecase.DeleteScoreUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ScoreRollbackConsumer(
    private val objectMapper: ObjectMapper,
    private val deleteScoreUseCase: DeleteScoreUseCase
) {
    @KafkaListener(
        topics = [KafkaTopics.CREATE_APPLICATION_STATUS_ROLLBACK],
        groupId = "create-application-score-status-rollback",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun execute(message: String) {
        val receiptCode = objectMapper.readValue(message, Long::class.java)
        deleteScoreUseCase.execute(receiptCode)
    }
}