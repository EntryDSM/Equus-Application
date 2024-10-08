package hs.kr.equus.application.domain.score.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.applicationCase.event.spi.ApplicationCaseEventPort
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.domain.score.usecase.UpdateScoreUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component

@Component
class ScoreUpdateQualificationCaseConsumer(
    private val mapper: ObjectMapper,
    private val updateScoreUseCase: UpdateScoreUseCase,
    private val applicationCaseEventPort: ApplicationCaseEventPort
)  {
    @Retryable(
        value = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 100)
    )
    @KafkaListener(
        topics = [KafkaTopics.UPDATE_QUALIFICATION_CASE],
        groupId = "update-score",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun updateScore(message: String) {
        val qualificationCase = mapper.readValue(message, QualificationCase::class.java)
        updateScoreUseCase.execute(qualificationCase.receiptCode)
    }

    @Recover
    fun recover(exception: Exception, message: String) {
        val originQualificationCase = mapper.readValue(message, ApplicationCase::class.java)
        applicationCaseEventPort.updateApplicationCaseRollback(originQualificationCase)
    }
}
