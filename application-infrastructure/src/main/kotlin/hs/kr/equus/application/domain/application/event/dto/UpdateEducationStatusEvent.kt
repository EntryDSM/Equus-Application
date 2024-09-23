package hs.kr.equus.application.domain.application.event.dto

import hs.kr.equus.application.domain.application.model.Application
import java.time.YearMonth

data class UpdateEducationStatusEvent(
    val application: Application,
    val graduateDate: YearMonth
)
