package hs.kr.equus.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EquusApplication

fun main(args: Array<String>) {
    runApplication<EquusApplication>(*args)
}
