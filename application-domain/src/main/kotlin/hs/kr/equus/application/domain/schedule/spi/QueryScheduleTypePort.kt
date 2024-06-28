package hs.kr.equus.application.domain.schedule.spi

import hs.kr.equus.application.domain.schedule.enums.ScheduleType
import hs.kr.equus.application.domain.schedule.model.Schedule

interface QueryScheduleTypePort {
    fun queryScheduleType(scheduleType: ScheduleType): Schedule?
}