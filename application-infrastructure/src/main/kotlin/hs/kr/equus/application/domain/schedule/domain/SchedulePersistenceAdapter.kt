package hs.kr.equus.application.domain.schedule.domain

import hs.kr.equus.application.domain.schedule.enums.ScheduleType
import hs.kr.equus.application.domain.schedule.model.Schedule
import hs.kr.equus.application.domain.schedule.spi.SchedulePort
import hs.kr.equus.application.global.feign.client.ScheduleClient
import org.springframework.stereotype.Component

@Component
class SchedulePersistenceAdapter(
    private val scheduleClient: ScheduleClient
): SchedulePort {
    override fun queryScheduleType(scheduleType: ScheduleType): Schedule? {
        return scheduleClient.queryScheduleByType(scheduleType.name)?.let {
            Schedule(
                scheduleType = it.scheduleType,
                date = it.date
            )
        }
    }
}