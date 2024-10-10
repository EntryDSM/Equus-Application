package hs.kr.equus.application.global.config

import feign.RetryableException
import hs.kr.equus.application.global.feign.exception.FeignExceptions
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.net.ConnectException
import java.time.Duration
import java.util.concurrent.TimeoutException

@Component
class Resilience4JConfig {
    @Bean
    fun globalCustomConfiguration(): Customizer<Resilience4JCircuitBreakerFactory> {
        val circuitBreakerConfig = CircuitBreakerConfig.custom()
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(50)
            .failureRateThreshold(50f)
            .slowCallRateThreshold(50f)
            .slowCallDurationThreshold(Duration.ofSeconds(10))
            .minimumNumberOfCalls(20)
            .permittedNumberOfCallsInHalfOpenState(10)
            .maxWaitDurationInHalfOpenState(Duration.ofSeconds(5))
            .waitDurationInOpenState(Duration.ofSeconds(10))
            .automaticTransitionFromOpenToHalfOpenEnabled(false)
            .recordExceptions(
                ConnectException::class.java,
                RetryableException::class.java,
                TimeoutException::class.java,
                FeignExceptions.FeignServerErrorException::class.java,
                RuntimeException::class.java
            )
            .build()

        val timeLimiterConfig = TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofSeconds(5))
            .build()

        return Customizer { factory: Resilience4JCircuitBreakerFactory ->
            factory.configureDefault { id: String? ->
                Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(timeLimiterConfig)
                    .circuitBreakerConfig(circuitBreakerConfig)
                    .build()
            }
        }
    }
}
