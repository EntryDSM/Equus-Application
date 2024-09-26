package hs.kr.equus.application.domain.score.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.score.usecase.DeleteScoreUseCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class DeleteScoreConsumer(
    private val mapper: ObjectMapper,
    private val deleteScoreUseCase: DeleteScoreUseCase
) {
    private val logger = LoggerFactory.getLogger(DeleteScoreConsumer::class.java)

    @KafkaListener(
        topics = [KafkaTopics.DELETE_USER],
        groupId = "delete-score-consumer",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun deleteScore(message: String) {
        try {
            val receiptCode = mapper.readValue(message, Long::class.java)
            logger.info("delete-score-consumer group 에서 $receiptCode 번 점수를 삭제합니다.")
            deleteScoreUseCase.execute(receiptCode)
            logger.info("$receiptCode 번 점수를 성공적으로 삭제하였습니다.")
        } catch (e: Exception) {
            logger.error("delete-score-consumer group 에서 점수를 삭제하던 중 오류가 발생하였습니다. $message", e)
            // TODO :: 재시도 로직 작성
        }

    }
}