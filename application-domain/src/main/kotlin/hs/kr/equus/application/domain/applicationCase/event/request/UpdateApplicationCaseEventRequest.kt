package hs.kr.equus.application.domain.applicationCase.event.request

import java.math.BigDecimal

data class UpdateApplicationCaseEventRequest(
    val attendanceScore: Int,
    val volunteerScore: BigDecimal,
    val thirdBeforeBeforeScore: BigDecimal,
    val thirdBeforeScore: BigDecimal,
    val thirdGradeScore: BigDecimal,
    val totalGradeScore: BigDecimal,
    val totalScore: BigDecimal,
    val receiptCode: Long,
)
