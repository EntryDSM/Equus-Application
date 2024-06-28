package hs.kr.equus.application.global.feign.client.dto.response

import hs.kr.equus.application.domain.schedule.enums.ScheduleType
import java.time.LocalDateTime

data class ScheduleInfoElement (
    val scheduleType: ScheduleType,
    val date: LocalDateTime
)