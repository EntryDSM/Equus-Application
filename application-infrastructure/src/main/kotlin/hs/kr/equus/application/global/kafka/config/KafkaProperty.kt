package hs.kr.equus.application.global.kafka.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("kafka")
class KafkaProperty(
    val serverAddress: String,
    val confluentApiKey: String,
    val confluentApiSecret: String
)
