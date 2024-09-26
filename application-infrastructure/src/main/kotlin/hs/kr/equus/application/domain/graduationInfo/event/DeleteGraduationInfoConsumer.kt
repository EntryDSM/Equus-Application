package hs.kr.equus.application.domain.graduationInfo.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.spi.CommandGraduationInfoPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class DeleteGraduationInfoConsumer(
    private val mapper: ObjectMapper,
    private val commandGraduationInfoPort: CommandGraduationInfoPort,
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val queryApplicationPort: QueryApplicationPort
) {
    private val logger = LoggerFactory.getLogger(DeleteGraduationInfoConsumer::class.java)

    @KafkaListener(
        topics = [KafkaTopics.DELETE_USER],
        groupId = "delete-graduation-info-consumer",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun deleteGraduationInfo(message: String) {
        try {
            val receiptCode = mapper.readValue(message, Long::class.java)
            logger.info("delete-graduation-info-consumer 에서 $receiptCode 번 졸업정보를 삭제합니다.")

            val application = queryApplicationPort.queryApplicationByReceiptCode(receiptCode) ?:
                throw ApplicationExceptions.ApplicationNotFoundException()

            val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(application) ?:
                throw GraduationInfoExceptions.GraduationNotFoundException()

            commandGraduationInfoPort.delete(graduationInfo)
            logger.info("$receiptCode 번 점수를 성공적으로 삭제하였습니다.")
        } catch (e: Exception) {
            logger.error("delete-score-consumer group 에서 점수를 삭제하던 중 오류가 발생하였습니다. $message", e)
            // TODO :: 재시도 로직 작성
        }
    }
}