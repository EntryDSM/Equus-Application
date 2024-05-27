package hs.kr.equus.application.global.feign

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("neis")
class NeisProperty(
    val key: String
)