package hs.kr.equus.application.domain.application.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.application.event.spi.ApplicationEventPort
import hs.kr.equus.application.domain.application.event.dto.UpdateEducationStatusEvent
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.time.YearMonth

@Component
class ApplicationProducer(
    private val mapper: ObjectMapper,
    private val createApplicationTemplate: KafkaTemplate<String, Any>,
    private val updateEducationalStatusTemplate: KafkaTemplate<String, Any>,
    private val submitApplicationFinalTemplate: KafkaTemplate<String, Any>,
    private val createApplicationRollbackTemplate: KafkaTemplate<String, Any>
) : ApplicationEventPort {
    override fun create(receiptCode: Long) {
        createApplicationTemplate.send(
            KafkaTopics.CREATE_APPLICATION,
            receiptCode,
        )
    }

    override fun updateEducationalStatusRollback(application: Application, graduateDate: YearMonth) {
        updateEducationalStatusTemplate.send(
            KafkaTopics.UPDATE_EDUCATIONAL_STATUS_ROLLBACK,
            UpdateEducationStatusEvent(application, graduateDate),
        )
    }

    override fun updateEducationalStatus(application: Application, graduateDate: YearMonth) {
        updateEducationalStatusTemplate.send(
            KafkaTopics.UPDATE_EDUCATIONAL_STATUS,
            UpdateEducationStatusEvent(application, graduateDate),
        )
    }

    override fun createApplicationScoreRollback(receiptCode: Long) {
        createApplicationRollbackTemplate.send(
            KafkaTopics.CREATE_APPLICATION_SCORE_ROLLBACK,
            receiptCode
        )
    }

    override fun submitApplicationFinal(receiptCode: Long) {
        submitApplicationFinalTemplate.send(
            KafkaTopics.SUBMIT_APPLICATION_FINAL,
            receiptCode
        )
    }
}
