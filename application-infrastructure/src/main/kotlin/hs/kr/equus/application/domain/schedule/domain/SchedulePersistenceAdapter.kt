package hs.kr.equus.application.domain.schedule.domain

import hs.kr.equus.application.domain.schedule.enums.Type
import hs.kr.equus.application.domain.schedule.model.Schedule
import hs.kr.equus.application.domain.schedule.spi.SchedulePort
import hs.kr.equus.application.global.feign.client.ScheduleClient
import org.springframework.stereotype.Component

@Component
class SchedulePersistenceAdapter(
    private val scheduleClient: ScheduleClient
): SchedulePort {
    override fun queryScheduleType(type: Type): Schedule? {
        return scheduleClient.queryScheduleByType(type.name)?.let {
            Schedule(
                type = it.type,
                date = it.date
            )
        }
    }
}