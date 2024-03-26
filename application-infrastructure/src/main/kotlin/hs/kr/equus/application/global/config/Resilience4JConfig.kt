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
            .slowCallRateThreshold(1f)
            .slowCallDurationThreshold(Duration.ofMillis(2000))
            .failureRateThreshold(1f)
            .minimumNumberOfCalls(100)
            .slidingWindowSize(1)
            .permittedNumberOfCallsInHalfOpenState(100)
            .maxWaitDurationInHalfOpenState(Duration.ofMillis(1000))
            .waitDurationInOpenState(Duration.ofMillis(3000))
            .automaticTransitionFromOpenToHalfOpenEnabled(false)
            .recordExceptions(
                ConnectException::class.java,
                RetryableException::class.java,
                TimeoutException::class.java,
                FeignExceptions.FeignServerErrorException::class.java
            )
            .build()

        val timeLimiterConfig = TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofSeconds(3))
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