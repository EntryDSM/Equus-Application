package hs.kr.equus.application.domain.applicationCase.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.spi.CommandApplicationCasePort
import hs.kr.equus.application.domain.applicationCase.spi.QueryApplicationCasePort
import hs.kr.equus.application.global.exception.BusinessException
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class DeleteApplicationCaseConsumer(
    private val mapper: ObjectMapper,
    private val commandApplicationCasePort: CommandApplicationCasePort,
    private val queryApplicationPort: QueryApplicationPort,
    private val queryApplicationCasePort: QueryApplicationCasePort
) {
    @KafkaListener(
        topics = [KafkaTopics.DELETE_USER],
        groupId = "delete-application-case-consumer",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun deleteApplication(message: String) {
        val receiptCode = mapper.readValue(message, Long::class.java)

        val application = queryApplicationPort.queryApplicationByReceiptCode(receiptCode) ?:
            throw ApplicationExceptions.ApplicationNotFoundException()

        val applicationCase = queryApplicationCasePort.queryApplicationCaseByApplication(application) ?:
            throw ApplicationCaseExceptions.ApplicationCaseNotFoundException()

        commandApplicationCasePort.delete(applicationCase)
    }
}