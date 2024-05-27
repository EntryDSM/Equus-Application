package hs.kr.equus.application.global.feign

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HeaderConfiguration {
    @Bean
    fun requestInterceptor(): RequestInterceptor = RequestInterceptor { template ->
        template.header("Request-User-Id", "\${feign.userId}")
        template.header("Request-User-Role", "ROOT")
    }
}
