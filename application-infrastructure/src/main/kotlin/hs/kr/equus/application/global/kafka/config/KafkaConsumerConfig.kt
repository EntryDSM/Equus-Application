package hs.kr.equus.application.global.kafka.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig(
    private val kafkaProperty: KafkaProperty,
) {
    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()

        factory.setConcurrency(2)
        factory.consumerFactory = DefaultKafkaConsumerFactory(consumerFactoryConfig())
        factory.containerProperties.pollTimeout = 500
        return factory
    }

    private fun consumerFactoryConfig(): Map<String, Any> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperty.serverAddress,
            ConsumerConfig.ISOLATION_LEVEL_CONFIG to "read_committed",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "false",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to 5000,
            JsonDeserializer.TRUSTED_PACKAGES to "*",
            "security.protocol" to "SASL_SSL",
            "sasl.mechanism" to "PLAIN",
            "sasl.jaas.config" to "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"${kafkaProperty.confluentApiKey}\" password=\"${kafkaProperty.confluentApiSecret}\";"
        )
    }
}