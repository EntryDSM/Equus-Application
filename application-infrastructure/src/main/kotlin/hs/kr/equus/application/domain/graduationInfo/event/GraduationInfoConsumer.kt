package hs.kr.equus.application.domain.graduationInfo.event

import hs.kr.equus.application.domain.graduationInfo.usecase.ChangeGraduationInfoUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class GraduationInfoConsumer(
    private val changeGraduationInfoUseCase: ChangeGraduationInfoUseCase,
) {
    @KafkaListener(
        topics = [KafkaTopics.UPDATE_EDUCATIONAL_STATUS],
        groupId = "change-graduation-info",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun changeGraduationInfo(@Payload receiptCode: Long) {
        changeGraduationInfoUseCase.execute(receiptCode)
    }
}
