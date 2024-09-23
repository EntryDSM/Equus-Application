package hs.kr.equus.application.domain.score.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.score.usecase.CreateScoreUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ScoreConsumer(
    private val mapper: ObjectMapper,
    private val createScoreUseCase: CreateScoreUseCase,
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
}
