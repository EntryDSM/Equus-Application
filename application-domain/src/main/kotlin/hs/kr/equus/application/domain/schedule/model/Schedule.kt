package hs.kr.equus.application.domain.schedule.model

import hs.kr.equus.application.domain.schedule.enums.ScheduleType
import hs.kr.equus.application.global.annotation.Aggregate
import java.time.LocalDateTime

@Aggregate
data class Schedule(
    val scheduleType: ScheduleType,
    val date: LocalDateTime
)