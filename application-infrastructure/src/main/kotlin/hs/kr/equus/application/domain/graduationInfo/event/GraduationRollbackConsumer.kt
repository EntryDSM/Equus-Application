package hs.kr.equus.application.domain.graduationInfo.event

import hs.kr.equus.application.domain.application.event.dto.UpdateEducationStatusEvent
import hs.kr.equus.application.domain.graduationInfo.usecase.ChangeGraduationInfoUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class GraduationRollbackConsumer(
    private val changeGraduationInfoUseCase: ChangeGraduationInfoUseCase
) {
    @KafkaListener(
        topics = [KafkaTopics.UPDATE_EDUCATIONAL_CASE_ROLLBACK],
        groupId = "rollback-update-education-case",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun changeGraduationInfo(dto: UpdateEducationStatusEvent) {
        dto.apply {
            changeGraduationInfoUseCase.execute(
                receiptCode = receiptCode,
                graduateDate = graduateDate
            )
        }
    }
}