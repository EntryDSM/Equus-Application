package hs.kr.equus.application.global.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("auth.jwt")
class JwtProperties(
    val secretKey: String,
    val header: String,
    val prefix: String
)