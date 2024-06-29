package hs.kr.equus.application.domain.schedule.spi

import hs.kr.equus.application.domain.schedule.enums.Type
import hs.kr.equus.application.domain.schedule.model.Schedule

interface QueryScheduleTypePort {
    fun queryScheduleType(type: Type): Schedule?
}