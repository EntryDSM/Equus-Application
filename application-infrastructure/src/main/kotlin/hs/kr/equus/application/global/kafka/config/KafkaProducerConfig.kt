package hs.kr.equus.application.global.kafka.config


import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.UUID

@Configuration
class KafkaProducerConfig(
    private val kafkaProperty: KafkaProperty,
) {
    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }

    private fun producerFactory(): ProducerFactory<String, Any> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    private fun producerConfig(): Map<String, Any> {
        return mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperty.serverAddress,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
            "security.protocol" to "SASL_PLAINTEXT",
            "sasl.mechanism" to "SCRAM-SHA-512",
            "sasl.jaas.config" to
                "org.apache.kafka.common.security.scram.ScramLoginModule required " +
                "username=\"${kafkaProperty.confluentApiKey}\" " +
                "password=\"${kafkaProperty.confluentApiSecret}\";"
        )
    }
}
