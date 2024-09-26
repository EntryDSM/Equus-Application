package hs.kr.equus.application.domain.score.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.score.usecase.DeleteScoreUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class DeleteScoreConsumer(
    private val mapper: ObjectMapper,
    private val deleteScoreUseCase: DeleteScoreUseCase
) {
    @KafkaListener(
        topics = [KafkaTopics.DELETE_USER],
        groupId = "delete-score-consumer",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun deleteScore(message: String) {
        val receiptCode = mapper.readValue(message, Long::class.java)
        deleteScoreUseCase.execute(receiptCode)
    }
}