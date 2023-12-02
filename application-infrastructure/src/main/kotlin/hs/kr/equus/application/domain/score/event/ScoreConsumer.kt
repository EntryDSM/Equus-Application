package hs.kr.equus.application.domain.score.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.score.usecase.CreateScoreUseCase
import hs.kr.equus.application.domain.score.usecase.UpdateScoreUseCase
import hs.kr.equus.application.domain.score.usecase.dto.request.UpdateScoreRequest
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ScoreConsumer(
    private val mapper: ObjectMapper,
    private val createScoreUseCase: CreateScoreUseCase,
    private val updateScoreUseCase: UpdateScoreUseCase,
) {
    @KafkaListener(
        topics = [KafkaTopics.CREATE_APPLICATION],
        groupId = "create-score",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun createScore(message: String) {
        val receiptCode = mapper.readValue(message, Long::class.java)
        createScoreUseCase.execute(receiptCode)
    }

    @KafkaListener(
        topics = [KafkaTopics.UPDATE_APPLICATION_CASE],
        groupId = "update-score",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun updateScore(message: String) {
        val updateScoreRequest = mapper.readValue(message, UpdateScoreRequest::class.java)
        updateScoreUseCase.execute(updateScoreRequest.receiptCode)
    }
}
