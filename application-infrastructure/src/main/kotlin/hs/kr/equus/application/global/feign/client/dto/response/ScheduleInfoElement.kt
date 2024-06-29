package hs.kr.equus.application.global.feign.client.dto.response

import hs.kr.equus.application.domain.schedule.enums.Type
import java.time.LocalDateTime

data class ScheduleInfoElement (
    val type: Type,
    val date: LocalDateTime
)