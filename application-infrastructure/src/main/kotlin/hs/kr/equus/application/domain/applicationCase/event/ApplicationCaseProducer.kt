package hs.kr.equus.application.domain.applicationCase.event

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.domain.applicationCase.event.spi.ApplicationCaseEventPort
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.global.kafka.config.KafkaTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ApplicationCaseProducer(
    private val updateApplicationCaseTemplate: KafkaTemplate<String, Any>,
    private val objectMapper: ObjectMapper
) : ApplicationCaseEventPort {
    override fun updateGraduationCase(originGraduationCase: GraduationCase) {
        updateApplicationCaseTemplate.send(
            KafkaTopics.UPDATE_GRADUATION_CASE,
            objectMapper.writeValueAsString(originGraduationCase),
        )
    }

    override fun updateQualificationCase(qualificationCase: QualificationCase) {
        updateApplicationCaseTemplate.send(
            KafkaTopics.UPDATE_QUALIFICATION_CASE,
            objectMapper.writeValueAsString(qualificationCase),
        )
    }

    override fun updateApplicationCaseRollback(originApplicationCase: ApplicationCase) {
        updateApplicationCaseTemplate.send(
            KafkaTopics.UPDATE_APPLICATION_CASE_ROLLBACK,
            objectMapper.writeValueAsString(originApplicationCase),
        )
    }
}
