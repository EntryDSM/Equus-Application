package hs.kr.equus.application.domain.score.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.application.event.spi.ApplicationEventPort
import hs.kr.equus.application.domain.score.usecase.CreateScoreUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component

@Component
class CreateScoreApplicationConsumer (
    private val mapper: ObjectMapper,
    private val createScoreUseCase: CreateScoreUseCase,
    private val applicationEventPort: ApplicationEventPort
) {
    @Retryable(
        value = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 100)
    )
    @KafkaListener(
        topics = [KafkaTopics.CREATE_APPLICATION],
        groupId = "create-score",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun createScore(message: String) {
        val receiptCode = mapper.readValue(message, Long::class.java)
        createScoreUseCase.execute(receiptCode)
    }

    @Recover
    fun recover(exception: Exception, message: String) {
        val receiptCode = mapper.readValue(message, Long::class.java)
        applicationEventPort.createApplicationScoreRollback(receiptCode)
    }
}