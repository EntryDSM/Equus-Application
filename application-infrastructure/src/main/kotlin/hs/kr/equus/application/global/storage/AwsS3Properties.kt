package hs.kr.equus.application.global.storage

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("cloud.aws.s3")
@ConstructorBinding
class AwsS3Properties(
    val bucket: String,
)
