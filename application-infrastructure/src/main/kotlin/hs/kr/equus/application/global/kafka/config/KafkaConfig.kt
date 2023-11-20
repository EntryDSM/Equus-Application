package hs.kr.equus.application.global.kafka.config

import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ser.std.StringSerializer
import hs.kr.equus.application.global.kafka.dto.UpdateStatusEventRequest
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate

@Configuration
class KafkaConfig(
    private val kafkaProperty: KafkaProperty,
) {
    @Bean
    fun createStatusProducerFactory(): DefaultKafkaProducerFactory<String, UpdateStatusEventRequest> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    @Bean
    fun createStatusKafkaTemplate(): KafkaTemplate<String, UpdateStatusEventRequest> {
        return KafkaTemplate(createStatusProducerFactory())
    }

    private fun producerConfig(): MutableMap<String, Any> {
        val configs: MutableMap<String, Any> = HashMap<String, Any>()
        configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperty.serverAddress
        configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return configs
    }
}
