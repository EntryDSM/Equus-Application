package hs.kr.equus.application.global.config

import hs.kr.equus.application.global.DomainProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("domain")
class DomainPropertiesConfig(
    model: Map<String, String>
) {
    init {
        DomainProperties.putAll(model)
    }
}