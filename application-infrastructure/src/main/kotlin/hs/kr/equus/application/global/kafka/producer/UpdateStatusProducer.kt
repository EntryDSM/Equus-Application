package hs.kr.equus.application.global.kafka.producer

import hs.kr.equus.application.global.kafka.config.KafkaTopics
import hs.kr.equus.application.global.kafka.dto.UpdateStatusEventRequest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class UpdateStatusProducer(
    private val kafkaTemplate: KafkaTemplate<String, UpdateStatusEventRequest>,
) {
    fun send(request: UpdateStatusEventRequest) {
        kafkaTemplate.send(
            KafkaTopics.UPDATE_STATUS,
            request,
        )
    }
}
