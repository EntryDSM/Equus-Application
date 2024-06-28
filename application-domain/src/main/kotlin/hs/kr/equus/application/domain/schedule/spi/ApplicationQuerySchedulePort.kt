package hs.kr.equus.application.domain.schedule.spi

import hs.kr.equus.application.domain.schedule.enums.ScheduleType
import hs.kr.equus.application.domain.schedule.model.Schedule

interface ApplicationQuerySchedulePort {
    fun queryByScheduleType(scheduleType: ScheduleType): Schedule?
}