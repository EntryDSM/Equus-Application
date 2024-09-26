package hs.kr.equus.application.domain.application.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.application.spi.CommandApplicationPort
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class DeleteApplicationConsumer(
    private val mapper: ObjectMapper,
    private val commandApplicationPort: CommandApplicationPort
) {
    private val logger = LoggerFactory.getLogger(DeleteApplicationConsumer::class.java)

    @KafkaListener(
        topics = [KafkaTopics.DELETE_USER],
        groupId = "delete-application-consumer",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun deleteApplication(message: String) {
        try {
            val receiptCode = mapper.readValue(message, Long::class.java)
            logger.info("delete-application-consumer groupId 에서 $receiptCode 번 원서를 삭제합니다.")
            commandApplicationPort.deleteByReceiptCode(receiptCode)
            logger.info("$receiptCode 번 원서를 성공적으로 삭제하였습니다.")
        } catch (e: Exception) {
            logger.error("delete-application-consumer groupId 에서 원서를 삭제하던 중 오류가 발생하였습니다. $message", e)
            // TODO :: 재시도 로직 작성
        }
    }
}