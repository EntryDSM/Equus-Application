package hs.kr.equus.application.global.kafka.producer

import hs.kr.equus.application.global.kafka.KafkaTopics
import hs.kr.equus.application.global.kafka.dto.CreateStatusEventRequest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class CreateStatusProducer(
    private val kafkaTemplate: KafkaTemplate<String, CreateStatusEventRequest>,
) {
    fun send(request: CreateStatusEventRequest) {
        kafkaTemplate.send(
            KafkaTopics.CREATE_STATUS,
            request,
        )
    }
}
