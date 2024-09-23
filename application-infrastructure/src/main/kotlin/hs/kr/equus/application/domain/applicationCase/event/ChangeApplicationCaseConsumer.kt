package hs.kr.equus.application.domain.applicationCase.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.applicationCase.usecase.ChangeApplicationCaseUseCase
import hs.kr.equus.application.domain.application.event.dto.UpdateEducationStatusEvent
import hs.kr.equus.application.domain.application.event.spi.ApplicationEventPort
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component

@Component
class ChangeApplicationCaseConsumer(
    private val mapper: ObjectMapper,
    private val applicationPort: ApplicationEventPort,
    private val changeApplicationCaseUseCase: ChangeApplicationCaseUseCase,
) {

    @Retryable(
        value = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 100)
    )
    @KafkaListener(
        topics = [KafkaTopics.UPDATE_EDUCATIONAL_STATUS],
        groupId = "update-application-case",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun changeApplicationCase(dto: UpdateEducationStatusEvent) {
        throw Exception()
        changeApplicationCaseUseCase.execute(dto.receiptCode)
    }

    @Recover
    fun recover(exception: Exception, dto: UpdateEducationStatusEvent) {
        applicationPort.updateEducationalStatusRollback(dto.receiptCode, dto.graduateDate)
    }
}
