package hs.kr.equus.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class EquusApplication

fun main(args: Array<String>) {
    runApplication<EquusApplication>(*args)
}
