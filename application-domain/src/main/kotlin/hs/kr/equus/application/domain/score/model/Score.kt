package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.global.annotation.domain.Aggregate
import java.math.BigDecimal
import java.util.*

@Aggregate
data class Score(
    val id: UUID,
    val attendanceScore: Int,
    val volunteerScore: BigDecimal,
    val thirdBeforeBeforeScore: BigDecimal,
    val thirdBeforeScore: BigDecimal,
    val thirdGradeScore: BigDecimal,
    val totalGradeScore: BigDecimal,
    val totalScore: BigDecimal,
)
